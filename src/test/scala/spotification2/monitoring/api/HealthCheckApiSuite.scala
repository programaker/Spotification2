package spotification2.monitoring.api

import cats.effect.IO
import cats.effect.kernel.Resource
import cats.syntax.all.*
import fs2.io.file.Files
import fs2.io.file.Path
import io.circe.parser.*
import munit.CatsEffectSuite
import sttp.client3.*
import sttp.client3.circe.*
import sttp.client3.testing.SttpBackendStub
import sttp.model.StatusCode
import sttp.tapir.integ.cats.effect.CatsMonadError
import sttp.tapir.server.stub.TapirStubInterpreter

import spotification2.common.api.GenericSuccess

final class HealthCheckApiSuite extends CatsEffectSuite:
  test("should always return a simple success message") {
    val response = basicRequest
      .get(uri"http://test.com/health")
      .response(asJson[GenericSuccess])
      .send(getHealthBackendStub(apiFix()))

    val status = response.map(_.code)
    val body = response.map(_.body.leftMap(_.toString()))

    assertIO(status, StatusCode.Ok) *> assertIO(body, expectedResponseFix())
  }

  val apiFix = ResourceSuiteLocalFixture("api", apiResource)
  val expectedResponseFix = ResourceSuiteLocalFixture("response", jsonResponseResource)

  override def munitFixtures = List(apiFix, expectedResponseFix)

  def apiResource: Resource[IO, HealthCheckApi] =
    Resource.pure(HealthCheckApi())

  def jsonResponseResource: Resource[IO, Either[String, GenericSuccess]] =
    Files[IO]
      .readUtf8Lines(Path("src/test/resources/monitoring/api/getHealthResponse.json"))
      .compile
      .foldMonoid
      .map(decode[GenericSuccess](_).leftMap(_.toString()))
      .toResource

  def getHealthBackendStub(api: HealthCheckApi): SttpBackend[IO, Nothing] =
    TapirStubInterpreter(SttpBackendStub(new CatsMonadError[IO]()))
      .whenServerEndpointRunLogic(api.getHealthServer)
      .backend()
