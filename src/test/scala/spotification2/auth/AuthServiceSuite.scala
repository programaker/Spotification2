package spotification2.auth

import cats.syntax.all.*
import munit.CatsEffectSuite
import org.scalacheck.Gen
import org.scalacheck.cats.implicits.*

import spotification2.common.UriString
import spotification2.common.UriStringP
import spotification2.common.syntax.refined.*
import spotification2.testutil.generator.Generators
import spotification2.testutil.syntax.gen.*

final class AuthServiceSuite extends CatsEffectSuite:
  test("should make the authorize URI"):
    val authorizeUri = "https://accounts.spotify.com/authorize".refineU[UriStringP].pipe(AuthorizeUri(_))
    val redirectUri = "http://test.com/authorization/spotify/callback".refineU[UriStringP].pipe(RedirectUri(_))

    val scope = List(
      "playlist-read-collaborative",
      "playlist-modify-public",
      "playlist-read-private",
      "playlist-modify-private",
      "user-read-private",
      "user-read-email",
      "user-follow-read"
    ).map(_.refineU[ScopeP]).some

    val req = Generators.genClientId.map { clientId =>
      AuthorizeRequest(
        clientId = clientId,
        redirectUri = redirectUri,
        responseType = AuthorizationResponseType.Code,
        state = None,
        scope = scope,
        showDialog = None
      )
    }.run()

    val res = AuthService.makeAuthorizeUri(authorizeUri, req)
