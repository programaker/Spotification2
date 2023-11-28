package spotification2.auth.api

import spotification2.common.api.MkServerEndpoint
import spotification2.common.api.ListServerEndpoints
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.*
import sttp.tapir.json.circe.*
import sttp.tapir.EndpointInput.Query
import spotification2.common.UriString
import io.circe.refined.*
import sttp.tapir.codec.refined.*
import sttp.model.StatusCode
import spotification2.auth.AccessTokenResponse
import spotification2.common.GenericError
import spotification2.common.BIO
import spotification2.auth.AuthorizeErrorResponse
import spotification2.common.NonBlankString
import spotification2.auth.AuthService
import spotification2.config.AuthConfig

private def baseEndpoint =
  endpoint
    .get
    .in("authorization" / "spotify")
    .errorOut(jsonBody[GenericError].and(statusCode(StatusCode.InternalServerError)))

private def callbackEndpoint =
  baseEndpoint.in("callback")

private def callbackCodeParam: Query[NonBlankString] =
  query("code")

private def callbackErrorParam: Query[NonBlankString] =
  query("error")

private def callbackStateParam: Query[Option[NonBlankString]] =
  query("state")

///

trait GetSpotifyAuthorization extends MkServerEndpoint:
  final def mkEndpoint =
    baseEndpoint
      .out(jsonBody[UriString].and(statusCode(StatusCode.Found)))

  override final def mkServerEndpoint: ServerEndpoint[Any, IO] =
    mkEndpoint.serverLogicPure(_ => logic)

  def logic: Either[GenericError, UriString]

///

trait GetCallback extends MkServerEndpoint:
  final def mkEndpoint =
    callbackEndpoint
      .in(callbackCodeParam)
      .in(callbackStateParam)
      .out(jsonBody[AccessTokenResponse])

  override final def mkServerEndpoint: ServerEndpoint[Any, IO] =
    mkEndpoint.serverLogic(logic)

  def logic(code: NonBlankString, state: Option[NonBlankString]): BIO[GenericError, AccessTokenResponse]

///

trait GetErrorCallback extends MkServerEndpoint:
  final def mkEndpoint =
    callbackEndpoint
      .in(callbackErrorParam)
      .in(callbackStateParam)
      .out(jsonBody[AuthorizeErrorResponse])

  override final def mkServerEndpoint: ServerEndpoint[Any, IO] =
    mkEndpoint.serverLogic(logic)

  def logic(error: NonBlankString, state: Option[NonBlankString]): BIO[GenericError, AuthorizeErrorResponse]

///

trait AuthApi extends ListServerEndpoints:
  def getSpotifyAuthorization: GetSpotifyAuthorization
  def getCallback: GetCallback
  def getErrorCallback: GetErrorCallback

  override final def serverEndpoints: List[ServerEndpoint[Any, IO]] =
    List(
      getSpotifyAuthorization,
      getCallback,
      getErrorCallback
    ).map(_.mkServerEndpoint)

object AuthApi:
  def apply(authService: AuthService, authConfig: AuthConfig): AuthApi = new:
    override def getSpotifyAuthorization: GetSpotifyAuthorization = new:
      override def logic: Either[GenericError, UriString] = ???

    override def getCallback: GetCallback = new:
      override def logic(
        code: NonBlankString,
        state: Option[NonBlankString]
      ): BIO[GenericError, AccessTokenResponse] =
        ???

    override def getErrorCallback: GetErrorCallback = new:
      override def logic(
        error: NonBlankString,
        state: Option[NonBlankString]
      ): BIO[GenericError, AuthorizeErrorResponse] =
        ???
