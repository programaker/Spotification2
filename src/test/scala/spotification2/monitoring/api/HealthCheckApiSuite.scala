package spotification2.monitoring.api

import munit.CatsEffectSuite
import sttp.tapir.server.stub.TapirStubInterpreter
import sttp.client3.testing.SttpBackendStub
import cats.effect.IO
import sttp.tapir.integ.cats.effect.CatsMonadError
import sttp.client3.*
import sttp.client3.circe.*
import spotification2.common.GenericResponse
import cats.effect.kernel.Resource
import fs2.io.file.Path
import fs2.io.file.Files
import sttp.model.StatusCode
import io.circe.syntax.*
import io.circe.Json
import mouse.feither.*
import cats.syntax.all.*

final class HealthCheckApiSuite extends CatsEffectSuite:
  test("should always return a simple success message") {
    val response = basicRequest
      .get(uri"http://test.com/health")
      .response(asJson[GenericResponse])
      .send(getHealthBackendStub(api()))

    response.map(_.code).pipe(assertIO(_, StatusCode.Ok)) *>
      response.map(_.body).mapIn(_.asJson).pipe(assertIO(_, jsonResponse().asRight))
  }

  val api = ResourceSuiteLocalFixture("api", apiResource)
  val jsonResponse = ResourceSuiteLocalFixture("response", jsonResponseResource)

  override def munitFixtures = List(api, jsonResponse)

  def apiResource: Resource[IO, HealthCheckApi] =
    Resource.pure(HealthCheckApi())

  def jsonResponseResource: Resource[IO, Json] =
    Files[IO]
      .readUtf8Lines(Path("src/test/resources/monitoring/api/getHealthResponse.json"))
      .compile
      .foldMonoid
      .toResource
      .map(_.asJson)

  def getHealthBackendStub(api: HealthCheckApi): SttpBackend[IO, Nothing] =
    TapirStubInterpreter(SttpBackendStub(new CatsMonadError[IO]()))
      .whenServerEndpointRunLogic(api.getHealth.server)
      .backend()
