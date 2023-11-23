package spotification2.log

import cats.effect.IO
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.LoggerName
import org.typelevel.log4cats.SelfAwareStructuredLogger
import org.typelevel.log4cats.slf4j.Slf4jFactory

type Log = LoggerFactory[IO]
object Log:
  private val logFactory: Log = Slf4jFactory.create
  inline def apply()(using LoggerName): SelfAwareStructuredLogger[IO] = logFactory.getLogger
