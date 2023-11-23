package spotification2.config

import cats.effect.IO
import mouse.feither.*
import pureconfig.ConfigSource

trait ConfigService:
  def loadConfig: IO[AppConfig]

object ConfigService:
  def apply(): ConfigService = new:
    override def loadConfig: IO[AppConfig] =
      IO.blocking(ConfigSource.default.load[AppConfig])
        .leftMapIn(_.prettyPrint().pipe(ConfigError.apply))
        .flatMap(IO.fromEither)
