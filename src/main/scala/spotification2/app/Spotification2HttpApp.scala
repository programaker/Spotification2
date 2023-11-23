package spotification2.app

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
import cats.syntax.all.*
import org.http4s.HttpRoutes

import spotification2.app.http.HttpServer
import spotification2.common.api.RoutesInterpreter
import spotification2.config.ConfigService
import spotification2.log.Log
import spotification2.monitoring.api.HealthCheckApi

import Log.given
import RoutesInterpreter.given

object Spotification2HttpApp extends IOApp:
  private val log = Log()

  override def run(args: List[String]): IO[ExitCode] =
    val appConfig = ConfigService().loadConfig
    val serverConfig = appConfig.map(_.server)

    serverConfig
      .flatMap(HttpServer.run(allRoutes, _))
      .as(ExitCode.Success)
      .handleErrorWith(handleError(_) *> ExitCode.Error.pure)

  private def allRoutes: HttpRoutes[IO] =
    HealthCheckApi().routes

  private def handleError(err: Throwable): IO[Unit] =
    log.error(err)("Application exited with error")
