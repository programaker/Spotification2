package spotification2.auth

import io.circe.derivation.ConfiguredCodec
import io.circe.refined.*
import sttp.tapir.Schema
import sttp.tapir.codec.refined.*

import spotification2.common.NonBlankString
import spotification2.common.json.SpotifyJSonConfig.given
import spotification2.config.AuthConfig

final case class AuthorizeRequest(
  clientId: ClientId,
  redirectUri: RedirectUri,
  responseType: AuthorizationResponseType,
  state: Option[NonBlankString],
  scope: Option[List[Scope]],
  showDialog: Option[Boolean]
) derives ConfiguredCodec,
      Schema

object AuthorizeRequest:
  def make(cfg: AuthConfig): AuthorizeRequest =
    AuthorizeRequest(
      clientId = cfg.clientId,
      redirectUri = cfg.redirectUri,
      responseType = AuthorizationResponseType.Code,
      state = None, // we'll not use it for now
      scope = cfg.scopes,
      showDialog = None // defaults to false, which is what we want
    )
