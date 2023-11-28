package spotification2.common.json

import io.circe.derivation.Configuration

// circe codec configuration: https://github.com/circe/circe/pull/1800

// Spotify API uses snake-case Json
object SpotifyJSonConfig:
  // Available to be further modified if necessary
  val config: Configuration =
    Configuration
      .default
      .withSnakeCaseConstructorNames
      .withSnakeCaseMemberNames

  // To import and use with circe's `ConfiguredCodec`
  given Configuration = config
