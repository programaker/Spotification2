package spotification2.monitoring.api

import sttp.tapir.*
import sttp.tapir.json.circe.*
import spotification2.common.GenericResponse
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint

object HealthCheckApi:
  def healthEndpoint: Endpoint[Unit, Unit, Unit, GenericResponse, Any] =
    endpoint
      .description("A health-check endpoint")
      .get
      .in("health")
      .out(jsonBody[GenericResponse].description("Just a text message"))

  def healthServer: ServerEndpoint[Any, IO] =
    healthEndpoint.serverLogicSuccess(_ => IO.pure(GenericResponse.success("I'm doing well, thanks for asking ^_^")))
