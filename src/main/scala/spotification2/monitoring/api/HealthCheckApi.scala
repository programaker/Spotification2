package spotification2.monitoring.api

import cats.effect.IO
import cats.syntax.applicative.*
import sttp.tapir.*
import sttp.tapir.json.circe.*
import sttp.tapir.server.ServerEndpoint

import spotification2.common.GenericResponse

trait GetHealth:
  final def mkEndpoint: Endpoint[Unit, Unit, Unit, GenericResponse, Any] =
    endpoint
      .description("A health-check endpoint")
      .get
      .in("health")
      .out(jsonBody[GenericResponse].description("Just a text message"))

  final def mkServerEndpoint: ServerEndpoint[Any, IO] =
    mkEndpoint.serverLogicSuccess(_ => logic)

  def logic: IO[GenericResponse]

///

trait HealthCheckApi:
  def getHealth: GetHealth
  final def serverEndpoints: List[ServerEndpoint[Any, IO]] = List(getHealth.mkServerEndpoint)

object HealthCheckApi:
  def apply(): HealthCheckApi = new:
    override def getHealth: GetHealth = new:
      override def logic: IO[GenericResponse] =
        GenericResponse.success("I'm doing well, thanks for asking ^_^").pure
