package spotification2.monitoring.api

import cats.effect.IO
import cats.syntax.applicative.*
import sttp.tapir.*
import sttp.tapir.json.circe.*

import spotification2.common.GenericResponse
import sttp.tapir.server.ServerEndpoint

trait HealthCheckApi:
  final def serverEndpoints: List[ServerEndpoint[Any, IO]] =
    List(getHealth.serverLogicSuccess(_ => getHealthLogic))

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
