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

// Http4s kinda sucks regarding performance.
// As Tapir abstracts the http server/framework, lets find something better!
// Search service is using NettyCats. Vertx seems very good too.
// https://www.techempower.com/benchmarks/#hw=ph&test=fortune&section=data-r22
// https://tapir.softwaremill.com/en/latest/

object HttpServer:
  def run(endpoints: List[ServerEndpoint[Any, IO]], serverConfig: ServerConfig): IO[Unit] =
    Dispatcher
      .parallel[IO]
      .flatMap { dispatcher =>
        Resource
          .make {
            IO.delay {
              val interpreter = VertxCatsServerInterpreter[IO](dispatcher)

              val vertx = Vertx.vertx()
              val server = vertx.createHttpServer(HttpServerOptions().setHost(serverConfig.host))
              val router = Router.router(vertx)

              endpoints.foreach(interpreter.route(_)(router))
              server.requestHandler(router).listen(serverConfig.port)
            }.flatMap(_.asF[IO])
          } { server =>
            IO.delay(server.close).flatMap(_.asF[IO].void)
          }
      }
      .use(_ => IO.never)
