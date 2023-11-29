package spotification2.monitoring.api

import cats.effect.IO
import cats.syntax.applicative.*
import sttp.tapir.*
import sttp.tapir.json.circe.*
import sttp.tapir.server.ServerEndpoint

import spotification2.common.api.GenericSuccess
import spotification2.common.api.ListServerEndpoints
import spotification2.common.api.MkServerEndpoint

trait GetHealth extends MkServerEndpoint:
  final def mkEndpoint =
    endpoint
      .description("A health-check endpoint")
      .get
      .in("health")
      .out(jsonBody[GenericSuccess].description("Just a text message"))

  final def mkServerEndpoint: ServerEndpoint[Any, IO] =
    mkEndpoint.serverLogicSuccess(_ => logic)

  def logic: IO[GenericSuccess]

///

trait HealthCheckApi extends ListServerEndpoints:
  def getHealth: GetHealth
  override final def serverEndpoints: List[ServerEndpoint[Any, IO]] = List(getHealth.mkServerEndpoint)

object HealthCheckApi:
  def apply(): HealthCheckApi = new:
    override def getHealth: GetHealth = new:
      override def logic: IO[GenericSuccess] =
        GenericSuccess.make("I'm doing well, thanks for asking ^_^").pure
