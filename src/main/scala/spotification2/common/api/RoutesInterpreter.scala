package spotification2.common.api

import sttp.tapir.server.http4s.Http4sServerInterpreter
import cats.effect.IO

type RoutesInterpreter = Http4sServerInterpreter[IO]
object RoutesInterpreter:
  given RoutesInterpreter = Http4sServerInterpreter()
