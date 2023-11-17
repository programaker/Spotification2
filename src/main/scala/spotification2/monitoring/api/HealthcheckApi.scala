package spotification2.monitoring.api

import sttp.tapir.*
import sttp.tapir.json.circe.*
import spotification2.common.GenericResponse
import cats.effect.IO
import org.http4s.HttpRoutes
import cats.syntax.applicative.*
import spotification2.common.api.RoutesInterpreter

trait HealthCheckApi:
  final def routes(using interpreter: RoutesInterpreter): HttpRoutes[IO] =
    interpreter.toRoutes(getHealth.serverLogicSuccess(_ => getHealthLogic))

  final def getHealth: Endpoint[Unit, Unit, Unit, GenericResponse, Any] =
    endpoint
      .description("A health-check endpoint")
      .get
      .in("health")
      .out(jsonBody[GenericResponse].description("Just a text message"))

  def getHealthLogic: IO[GenericResponse]

object HealthCheckApi:
  def apply(): HealthCheckApi = new:
    override def getHealthLogic: IO[GenericResponse] =
      GenericResponse.success("I'm doing well, thanks for asking ^_^").pure
