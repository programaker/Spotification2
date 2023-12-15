package spotification2.auth.api

import cats.effect.IO
import cats.syntax.all.*
import io.circe.refined.*
import mouse.feither.*
import sttp.model.StatusCode
import sttp.tapir.*
import sttp.tapir.EndpointInput.Query
import sttp.tapir.codec.refined.*
import sttp.tapir.json.circe.*
import sttp.tapir.server.ServerEndpoint

import spotification2.auth.AccessTokenRequest
import spotification2.auth.AccessTokenResponse
import spotification2.auth.AuthorizeRequest
import spotification2.auth.CallbackErrorRequest
import spotification2.auth.service.AuthService
import spotification2.common.BIO
import spotification2.common.NonBlankString
import spotification2.common.UriString
import spotification2.common.api.ApiServerEndpoints
import spotification2.common.api.GenericError
import spotification2.config.AuthConfig

private val baseEndpoint =
  endpoint
    .get
    .in("authorization" / "spotify")
    .errorOut(jsonBody[GenericError].and(statusCode(StatusCode.InternalServerError)))

private val callbackEndpoint =
  baseEndpoint.in("callback")

private val callbackCodeParam: Query[NonBlankString] =
  query("code")

private val callbackErrorParam: Query[NonBlankString] =
  query("error")

private val callbackStateParam: Query[Option[NonBlankString]] =
  query("state")

///

trait GetSpotifyAuthorization:
  val getSpotifyAuthorization =
    baseEndpoint.out(jsonBody[UriString].and(statusCode(StatusCode.Found)))

  val getSpotifyAuthorizationServer: ServerEndpoint[Any, IO] =
    getSpotifyAuthorization.serverLogicPure(_ => getSpotifyAuthorizationLogic)

  def getSpotifyAuthorizationLogic: Either[GenericError, UriString]

///

trait GetCallback:
  val getCallback =
    callbackEndpoint
      .in(callbackCodeParam)
      .in(callbackStateParam)
      .out(jsonBody[AccessTokenResponse])

  val getCallbackServer: ServerEndpoint[Any, IO] =
    getCallback.serverLogic(getCallbackLogic)

  def getCallbackLogic(
    code: NonBlankString,
    state: Option[NonBlankString]
  ): BIO[GenericError, AccessTokenResponse]

///

trait GetErrorCallback:
  val getErrorCallback =
    callbackEndpoint
      .in(callbackErrorParam)
      .in(callbackStateParam)
      .out(jsonBody[GenericError])

  val getErrorCallbackServer: ServerEndpoint[Any, IO] =
    getErrorCallback.serverLogicError(getErrorCallbackLogic)

  def getErrorCallbackLogic(
    error: NonBlankString,
    state: Option[NonBlankString]
  ): IO[GenericError]

///

trait AuthApi extends ApiServerEndpoints, GetSpotifyAuthorization, GetCallback, GetErrorCallback:
  override def apiServerEndpoints: List[ServerEndpoint[Any, IO]] = List(
    getSpotifyAuthorizationServer,
    getCallbackServer,
    getErrorCallbackServer
  )

object AuthApi:
  def apply(authService: AuthService, authConfig: AuthConfig): AuthApi = new:
    override def getSpotifyAuthorizationLogic: Either[GenericError, UriString] =
      AuthService
        .makeAuthorizeUri(AuthorizeRequest.fromConfig(authConfig))
        .leftMap(GenericError.make)

    override def getCallbackLogic(
      code: NonBlankString,
      state: Option[NonBlankString]
    ): BIO[GenericError, AccessTokenResponse] =
      authService
        .requestAccessToken(AccessTokenRequest.fromConfig(authConfig, code))
        .attempt
        .leftMapIn(GenericError.fromThrowable)

    override def getErrorCallbackLogic(
      error: NonBlankString,
      state: Option[NonBlankString]
    ): IO[GenericError] =
      authService.callbackError(CallbackErrorRequest.make(error, state))
