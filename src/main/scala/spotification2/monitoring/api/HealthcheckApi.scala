package spotification2.monitoring.api

import sttp.tapir.*
import sttp.tapir.json.circe.*
import spotification2.common.GenericResponse
import cats.effect.IO
import sttp.tapir.server.http4s.Http4sServerInterpreter
import org.http4s.HttpRoutes

object HealthCheckApi:
  def getHealth: Endpoint[Unit, Unit, Unit, GenericResponse, Any] =
    endpoint
      .description("A health-check endpoint")
      .get
      .in("health")
      .out(jsonBody[GenericResponse].description("Just a text message"))

  def routes(using interpreter: Http4sServerInterpreter[IO]): HttpRoutes[IO] =
    val program = (_: Unit) => IO.pure(GenericResponse.success("I'm doing well, thanks for asking ^_^"))
    val serverLogic = getHealth.serverLogicSuccess(program)
    interpreter.toRoutes(serverLogic)
