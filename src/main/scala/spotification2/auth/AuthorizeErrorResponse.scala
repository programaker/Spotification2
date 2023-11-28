package spotification2.auth

import spotification2.common.NonBlankString
import sttp.tapir.Schema
import spotification2.common.json.SpotifyJSonConfig.given
import io.circe.derivation.ConfiguredCodec
import io.circe.refined.*
import sttp.tapir.codec.refined.*

final case class AuthorizeErrorResponse(error: NonBlankString) derives ConfiguredCodec, Schema
