package spotification2.app.http

import cats.effect.IO
import cats.effect.kernel.Resource
import cats.effect.std.Dispatcher
import eu.timepit.refined.auto.*
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.netty.cats.NettyCatsServer
import sttp.tapir.server.netty.cats.NettyCatsServerBinding
import sttp.tapir.server.netty.cats.NettyCatsServerInterpreter
import sttp.tapir.server.netty.cats.NettyCatsServerOptions

import spotification2.config.ServerConfig

// Http4s kinda sucks regarding performance.
// As Tapir abstracts the http server/framework, lets find something better!
// Search service is using NettyCats. Vertx seems very good too.
// https://www.techempower.com/benchmarks/#hw=ph&test=fortune&section=data-r22
// https://tapir.softwaremill.com/en/latest/

object HttpServer:
  def run(endpoints: List[ServerEndpoint[Any, IO]], serverConfig: ServerConfig): IO[Unit] =
    val makeHttpServer = (dispatcher: Dispatcher[IO]) =>
      // Server log configuration
      // https://tapir.softwaremill.com/en/latest/server/debugging.html
      val serverLog = NettyCatsServerOptions.defaultServerLog[IO]

      // Interceptors configuration
      // v1 had CORS, so we might need the CORSInterceptor
      // https://tapir.softwaremill.com/en/latest/server/interceptors.html
      val serverOptions = NettyCatsServerOptions
        .customiseInterceptors(dispatcher)
        .serverLog(serverLog)
        .options

      val interpreter = NettyCatsServerInterpreter(serverOptions)

      NettyCatsServer(serverOptions)
        .host(serverConfig.host)
        .port(serverConfig.port)
        .addRoute(interpreter.toRoute(endpoints))
        .start()

    val releaseHttpServer = (server: NettyCatsServerBinding[IO]) => server.stop()

    Dispatcher
      .parallel[IO]
      .flatMap(makeHttpServer(_).pipe(Resource.make(_)(releaseHttpServer)))
      .use(_ => IO.never)
