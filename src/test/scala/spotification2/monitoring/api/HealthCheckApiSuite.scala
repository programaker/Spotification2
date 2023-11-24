package spotification2.monitoring.api

import munit.CatsEffectSuite
import sttp.tapir.server.stub.TapirStubInterpreter
import sttp.client3.testing.SttpBackendStub
import cats.effect.IO
import sttp.tapir.integ.cats.effect.CatsMonadError
import sttp.client3.*
import sttp.client3.circe.*
import spotification2.common.GenericResponse

final class HealthCheckApiSuite extends CatsEffectSuite:
  private val api = HealthCheckApi()

  private val backendStub = TapirStubInterpreter(SttpBackendStub(new CatsMonadError[IO]()))
    .whenServerEndpointRunLogic(api.getHealth.server)
    .backend()

  test("should always return a simple success message") {
    val response = basicRequest
      .get(uri"http://test.com/health")
      .response(asJson[GenericResponse])
      .send(backendStub)
  }
    
