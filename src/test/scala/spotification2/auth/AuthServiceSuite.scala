package spotification2.auth

import cats.syntax.all.*
import munit.CatsEffectSuite

import spotification2.common.UriString
import spotification2.common.UriStringP
import spotification2.common.syntax.refined.*
import spotification2.common.HexString32P
import spotification2.common.RefinementError

final class AuthServiceSuite extends CatsEffectSuite:
  test("should make the authorize URI") {
    val clientId = "b9d95de901e2d5b87da7149fa746c19a".refineU[HexString32P].pipe(ClientId(_))
    val authorizeUri = "https://accounts.spotify.com/authorize".refineU[UriStringP].pipe(AuthorizeUri(_))
    val redirectUri = "http://test.com/authorization/spotify/callback".refineU[UriStringP].pipe(RedirectUri(_))

    val scope = List(
      "playlist-read-collaborative",
      "playlist-modify-public",
      "playlist-read-private"
    ).map(_.refineU[ScopeP]).some

    val req = AuthorizeRequest(
      clientId = clientId,
      redirectUri = redirectUri,
      responseType = AuthorizationResponseType.Code,
      state = None,
      scope = scope,
      showDialog = None
    )

    val expected =
      ("https://accounts.spotify.com/authorize?" +
        "client_id=b9d95de901e2d5b87da7149fa746c19a&" +
        "response_type=code&" +
        "redirect_uri=http%3A%2F%2Ftest.com%2Fauthorization%2Fspotify%2Fcallback&" +
        "scope=playlist-read-collaborative+playlist-modify-public+playlist-read-private")
        .refineU[UriStringP]
        .asRight[RefinementError]

    val actual = AuthService.makeAuthorizeUri(authorizeUri, req)
    assertEquals(actual, expected)
  }