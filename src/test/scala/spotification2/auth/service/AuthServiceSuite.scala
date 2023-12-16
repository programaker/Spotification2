package spotification2.auth.service

import cats.syntax.all.*
import munit.CatsEffectSuite

import spotification2.auth.*
import spotification2.common.HexString32P
import spotification2.common.RefinementError
import spotification2.common.UriString
import spotification2.common.UriStringP
import spotification2.common.syntax.refined.*
import cats.effect.kernel.Resource
import cats.effect.IO
import spotification2.common.api.GenericError

final class AuthServiceSuite extends CatsEffectSuite:
  val authServiceFix = ResourceSuiteLocalFixture("authService", authServiceResource)

  override def munitFixtures = List(authServiceFix)

  test("make the authorize URI") {
    val clientId = "b9d95de901e2d5b87da7149fa746c19a".refineU[HexString32P].pipe(ClientId(_))
    val authorizeUri = "https://accounts.spotify.com/authorize".refineU[UriStringP].pipe(AuthorizeUri(_))
    val redirectUri = "http://test.com/authorization/spotify/callback".refineU[UriStringP].pipe(RedirectUri(_))

    val scope = List(
      "playlist-read-collaborative",
      "playlist-modify-public",
      "playlist-read-private"
    ).map(_.refineU[ScopeP]).some

    val req = AuthorizeRequest.make(
      authorizeUri = authorizeUri,
      clientId = clientId,
      redirectUri = redirectUri,
      scope = scope
    )

    val expected =
      ("https://accounts.spotify.com/authorize?" +
        "client_id=b9d95de901e2d5b87da7149fa746c19a&" +
        "response_type=code&" +
        "redirect_uri=http%3A%2F%2Ftest.com%2Fauthorization%2Fspotify%2Fcallback&" +
        "scope=playlist-read-collaborative+playlist-modify-public+playlist-read-private")
        .refineE[UriStringP]

    val actual = service.AuthService.makeAuthorizeUri(req)
    assertEquals(actual, expected)
  }

  test("callback error") {
    val req = CallbackErrorRequest.make(
      error = "access_denied".refineU,
      state = None
    )

    val expectedResp = GenericError.make("access_denied")
    val resp = authServiceFix().callbackError(req)

    assertIO(resp, expectedResp)
  }

  test("request access token is successful") {
    val req = AccessTokenRequest.make(
      apiTokenUri = ???, // ApiTokenUri,
      clientId = ???, // ClientId,
      clientSecret = ???, // ClientSecret,
      redirectUri = ???, // RedirectUri,
      code = ??? // NonBlankString
    )
  }

  def authServiceResource: Resource[IO, AuthService] =
    AuthService(WorkaroundServiceMock).pipe(Resource.pure)

///

object WorkaroundServiceMock extends WorkaroundService:
  override def post(uri: String, body: String, headers: Map[String, String]): IO[String] =
    IO.pure(mockAccessTokenResponse)

  private val mockAccessTokenResponse = """{
    "access_token": "ATATAT",
    "token_type": "Bearer",
    "scope": "playlist-read-collaborative playlist-modify-public playlist-read-private",
    "expires_in": 120,
    "refresh_token": "RTRTRT"
  }"""
