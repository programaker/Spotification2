package spotification2.auth

import io.circe.derivation.ConfiguredCodec
import io.circe.refined.*
import sttp.tapir.Schema
import sttp.tapir.codec.refined.*

import spotification2.common.PositiveInt
import spotification2.common.json.SpotifyJSonConfig.given

final case class AccessTokenResponse(
  accessToken: AccessToken,
  refreshToken: RefreshToken,
  expiresIn: PositiveInt,
  scope: Option[ScopeString],
  tokenType: TokenType
) derives ConfiguredCodec,
      Schema
