package spotification2.config

final case class ConfigError(message: String) extends Exception(message)
