package spotification2.monitoring.api

import cats.effect.IO
import cats.syntax.applicative.*
import sttp.tapir.*
import sttp.tapir.json.circe.*
import sttp.tapir.server.ServerEndpoint

import spotification2.common.api.ApiServerEndpoints
import spotification2.common.api.GenericSuccess

trait GetHealth:
  val getHealth =
    endpoint
      .description("A health-check endpoint")
      .get
      .in("health")
      .out(jsonBody[GenericSuccess].description("Just a text message"))

  val getHealthServer: ServerEndpoint[Any, IO] =
    getHealth.serverLogicSuccess(_ => getHealthLogic)

  def getHealthLogic: IO[GenericSuccess]

///

trait HealthCheckApi extends ApiServerEndpoints, GetHealth:
  override def apiServerEndpoints: List[ServerEndpoint[Any, IO]] = List(getHealthServer)

object HealthCheckApi:
  def apply(): HealthCheckApi = new:
    override val getHealthLogic: IO[GenericSuccess] =
      GenericSuccess.make("I'm doing well, thanks for asking ^_^").pure
