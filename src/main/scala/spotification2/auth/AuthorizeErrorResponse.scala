package spotification2.auth

import io.circe.derivation.ConfiguredCodec
import io.circe.refined.*
import sttp.tapir.Schema
import sttp.tapir.codec.refined.*

import spotification2.common.NonBlankString
import spotification2.common.json.SpotifyJSonConfig.given

final case class AuthorizeErrorResponse(error: NonBlankString) derives ConfiguredCodec, Schema
