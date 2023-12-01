package spotification2.app

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
import cats.syntax.all.*
import sttp.tapir.server.ServerEndpoint

import spotification2.app.http.HttpServer
import spotification2.config.ConfigService
import spotification2.log.Log
import spotification2.monitoring.api.HealthCheckApi

object Spotification2HttpApp extends IOApp:
  private val log = Log()

  override def run(args: List[String]): IO[ExitCode] =
    val appConfig = ConfigService().loadConfig
    val serverConfig = appConfig.map(_.server)

    serverConfig
      .flatMap(HttpServer.run(allEndpoints, _))
      .as(ExitCode.Success)
      .handleErrorWith(handleError(_) *> ExitCode.Error.pure)

  private def allEndpoints: List[ServerEndpoint[Any, IO]] =
    HealthCheckApi().apiServerEndpoints

  private def handleError(err: Throwable): IO[Unit] =
    log.error(err)("Application exited with error")
