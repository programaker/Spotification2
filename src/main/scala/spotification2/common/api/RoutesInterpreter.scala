package spotification2.common.api

import sttp.tapir.server.http4s.Http4sServerInterpreter
import cats.effect.IO

// Http4s kinda sucks regarding performance.
// As Tapir abstracts the http server/framework, lets find something better!
// Search service is using NettyCats. Vertx seems very good too.
// https://www.techempower.com/benchmarks/#hw=ph&test=fortune&section=data-r22
// https://tapir.softwaremill.com/en/latest/

type RoutesInterpreter = Http4sServerInterpreter[IO]
object RoutesInterpreter:
  given RoutesInterpreter = Http4sServerInterpreter()
