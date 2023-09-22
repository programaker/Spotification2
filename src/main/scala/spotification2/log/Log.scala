package spotification2.log

import cats.effect.IO
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory

type Log = LoggerFactory[IO]
object Log:
  given Log = Slf4jFactory.create
