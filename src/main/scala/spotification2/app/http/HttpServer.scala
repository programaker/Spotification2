package spotification2.app.http

import cats.effect.IO
import org.http4s.blaze.server.BlazeServerBuilder
import spotification2.config.ServerConfig
import eu.timepit.refined.auto.*
import org.http4s.server.middleware.CORS
import org.http4s.HttpApp
import org.http4s.server.middleware.Logger
import org.http4s.HttpRoutes
import org.http4s.server.Router

object HttpServer:
  def run(routes: HttpRoutes[IO], serverConfig: ServerConfig): IO[Unit] =
    BlazeServerBuilder[IO]
      .bindHttp(serverConfig.port, serverConfig.host)
      .withHttpApp(makeHttpApp(routes).pipe(addLogger).pipe(addCors))
      .serve
      .compile
      .drain

  private def makeHttpApp(routes: HttpRoutes[IO]): HttpApp[IO] =
    Router("/" -> routes).orNotFound

  private def addLogger(httpApp: HttpApp[IO]): HttpApp[IO] =
    Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

  private def addCors(httpApp: HttpApp[IO]): HttpApp[IO] =
    CORS.policy(httpApp)
