package spotification2.log

import org.typelevel.log4cats.Logger
import cats.effect.IO
import org.typelevel.log4cats.slf4j.Slf4jLogger

type Log = Logger[IO]
object Log:
  def make: IO[Log] = Slf4jLogger.create[IO]
