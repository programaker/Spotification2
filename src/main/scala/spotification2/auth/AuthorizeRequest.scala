package spotification2.auth

import spotification2.common.NonBlankString
import spotification2.config.AuthConfig

import AuthorizeRequest.*

final case class AuthorizeRequest(authorizeUri: AuthorizeUri, query: Query)

object AuthorizeRequest:
  final case class Query(
    clientId: ClientId,
    redirectUri: RedirectUri,
    responseType: AuthorizationResponseType,
    state: Option[NonBlankString],
    scope: Option[List[Scope]],
    showDialog: Option[Boolean]
  )

  def fromConfig(cfg: AuthConfig): AuthorizeRequest =
    make(
      authorizeUri = cfg.authorizeUri,
      clientId = cfg.clientId,
      redirectUri = cfg.redirectUri,
      scope = cfg.scopes
    )

  def make(
    authorizeUri: AuthorizeUri,
    clientId: ClientId,
    redirectUri: RedirectUri,
    scope: Option[List[Scope]]
  ): AuthorizeRequest =
    AuthorizeRequest(
      authorizeUri = authorizeUri,
      query = Query(
        clientId = clientId,
        redirectUri = redirectUri,
        responseType = AuthorizationResponseType.Code,
        state = None, // we'll not use it for now
        scope = scope,
        showDialog = None // defaults to false, which is what we want
      )
    )
