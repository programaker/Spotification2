package spotification2.auth

import spotification2.common.NonBlankString
import spotification2.config.AuthConfig

import AccessTokenRequest.*

final case class AccessTokenRequest(apiTokenUri: ApiTokenUri, header: Header, body: Body)

object AccessTokenRequest:
  final case class Header(
    clientId: ClientId,
    clientSecret: ClientSecret
  )

  final case class Body(
    grantType: AccessTokenGrantType,
    redirectUri: RedirectUri,
    code: NonBlankString
  )

  def make(
    apiTokenUri: ApiTokenUri,
    clientId: ClientId,
    clientSecret: ClientSecret,
    redirectUri: RedirectUri,
    code: NonBlankString
  ): AccessTokenRequest =
    AccessTokenRequest(
      apiTokenUri = apiTokenUri,
      header = Header(
        clientId = clientId,
        clientSecret = clientSecret
      ),
      body = Body(
        grantType = AccessTokenGrantType.AuthorizationCode,
        redirectUri = redirectUri,
        code = code
      )
    )

  def fromConfig(cfg: AuthConfig, code: NonBlankString): AccessTokenRequest =
    make(
      apiTokenUri = cfg.apiTokenUri,
      clientId = cfg.clientId,
      clientSecret = cfg.clientSecret,
      redirectUri = cfg.redirectUri,
      code = code
    )
