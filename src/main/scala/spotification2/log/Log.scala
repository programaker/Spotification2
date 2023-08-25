package spotification2.log

import cats.effect.IO
import cats.effect.kernel.Resource
import io.odin.Level
import io.odin.Logger
import io.odin.consoleLogger
import io.odin.formatter.Formatter
import io.odin.syntax.*

type Log = Logger[IO]
object Log:
  def resource: Resource[IO, Log] =
    consoleLogger[IO](
      formatter = Formatter.colorful,
      minLevel = Level.Info
    ).withAsync()
