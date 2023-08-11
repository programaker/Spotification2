package spotification2.auth

import eu.timepit.refined.generic.Equal
import eu.timepit.refined.api.Refined
import spotification2.common.syntax.refined.*
import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.boolean.Or
import spotification2.common.Opaque
import spotification2.common.NonBlankString
import spotification2.common.HexString32
import spotification2.common.UriString

type AuthorizationResponseTypeP = Equal["code"] // it's the only one that appeared until now
type AuthorizationResponseType = String Refined AuthorizationResponseTypeP
object AuthorizationResponseType:
  val Code: AuthorizationResponseType = "code".refineU

type AccessTokenGrantTypeP = Equal["authorization_code"]
type AccessTokenGrantType = String Refined AccessTokenGrantTypeP
object AccessTokenGrantType:
  val AuthorizationCode: AccessTokenGrantType = "authorization_code".refineU

type RefreshTokenGrantTypeP = Equal["refresh_token"]
type RefreshTokenGrantType = String Refined RefreshTokenGrantTypeP
object RefreshTokenGrantType:
  val RefreshToken: RefreshTokenGrantType = "refresh_token".refineU

type TokenTypeP = Equal["Bearer"]
type TokenType = String Refined TokenTypeP

// ScopeString = space-separated kebab-case strings
// ex: "playlist-read-private playlist-modify-private playlist-modify-public"
type ScopeStringP = MatchesRegex["""^[a-z]([a-z-])+(\s([a-z-])+)*[a-z]$"""]
type ScopeString = String Refined ScopeStringP

type PlaylistScopeP =
  Equal["playlist-read-collaborative"] Or Equal["playlist-modify-public"] Or Equal["playlist-read-private"] Or
    Equal["playlist-modify-private"]

type UserScopeP =
  Equal["user-read-private"] Or Equal["user-read-email"] Or Equal["user-follow-read"]

type ScopeP = PlaylistScopeP Or UserScopeP
type Scope = String Refined ScopeP

object AccessToken extends Opaque[NonBlankString]
type AccessToken = AccessToken.OpaqueType

object RefreshToken extends Opaque[NonBlankString]
type RefreshToken = RefreshToken.OpaqueType

object ClientId extends Opaque[HexString32]
type ClientId = ClientId.OpaqueType

object ClientSecret extends Opaque[HexString32]
type ClientSecret = ClientSecret.OpaqueType

object AuthorizeUri extends Opaque[UriString]
type AuthorizeUri = AuthorizeUri.OpaqueType

object ApiTokenUri extends Opaque[UriString]
type ApiTokenUri = ApiTokenUri.OpaqueType

object RedirectUri extends Opaque[UriString]
type RedirectUri = RedirectUri.OpaqueType
