package spotification2.app.http

import cats.effect.IO
import eu.timepit.refined.auto.*

import spotification2.config.ServerConfig
import cats.effect.std.Dispatcher
import cats.effect.kernel.Resource
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import sttp.tapir.server.vertx.cats.VertxCatsServerInterpreter
import sttp.tapir.server.vertx.cats.VertxCatsServerInterpreter.*
import io.vertx.core.http.HttpServerOptions
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.vertx.cats.VertxCatsServerOptions
import io.vertx.core.http.HttpServer as vtxHttpServer

// Http4s kinda sucks regarding performance.
// As Tapir abstracts the http server/framework, lets find something better!
// Search service is using NettyCats. Vertx seems very good too.
// https://www.techempower.com/benchmarks/#hw=ph&test=fortune&section=data-r22
// https://tapir.softwaremill.com/en/latest/

object HttpServer:
  def run(endpoints: List[ServerEndpoint[Any, IO]], serverConfig: ServerConfig): IO[Unit] =
    val makeHttpServer = (dispatcher: Dispatcher[IO]) =>
      val serverOptions = VertxCatsServerOptions
        .customiseInterceptors[IO](dispatcher)
        .options

      val httpServerOptions = HttpServerOptions()
        .setHost(serverConfig.host)
        .setLogActivity(true)

      val vertx = Vertx.vertx()
      val server = vertx.createHttpServer(httpServerOptions)
      val router = Router.router(vertx)
      val interpreter = VertxCatsServerInterpreter[IO](serverOptions)

      endpoints.foreach(interpreter.route(_)(router))
      server
        .requestHandler(router)
        .listen(serverConfig.port)
        .pipe(IO.delay)
        .flatMap(_.asF[IO])

    val releaseHttpServer = (server: vtxHttpServer) => IO.delay(server.close).flatMap(_.asF[IO].void)

    Dispatcher
      .parallel[IO]
      .flatMap(makeHttpServer(_).pipe(Resource.make(_)(releaseHttpServer)))
      .use(_ => IO.never)
