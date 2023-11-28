package spotification2.auth

import spotification2.common.PositiveInt
import sttp.tapir.Schema
import io.circe.refined.*
import sttp.tapir.codec.refined.*
import io.circe.derivation.ConfiguredCodec
import spotification2.common.json.SpotifyJSonConfig.given

final case class AccessTokenResponse(
  accessToken: AccessToken,
  refreshToken: RefreshToken,
  expiresIn: PositiveInt,
  scope: Option[ScopeString],
  tokenType: TokenType
) derives ConfiguredCodec,
      Schema
