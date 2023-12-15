package spotification2.auth.service

import cats.effect.IO
import cats.syntax.all.*
import eu.timepit.refined.cats.*
import io.circe.parser.*

import spotification2.auth.*
import spotification2.common.RefinementError
import spotification2.common.UriString
import spotification2.common.syntax.refined.*
import spotification2.common.api.GenericError

trait AuthService:
  def requestAccessToken(req: AccessTokenRequest): IO[AccessTokenResponse]
  def callbackError(req: CallbackErrorRequest): IO[GenericError]

object AuthService:
  def apply(workaroundService: WorkaroundService): AuthService = new:
    override def requestAccessToken(req: AccessTokenRequest): IO[AccessTokenResponse] =
      val body = req.body
      val formBody = Iterator(
        "grant_type"   -> body.grantType.show,
        "redirect_uri" -> body.redirectUri.show,
        "code"         -> body.code.show
      )
        .map((k, v) => show"$k=${WorkaroundService.encode(v)}")
        .toList
        .mkString_("&")

      val header = req.header
      val credentials = WorkaroundService.base64Credentials(header.clientId, header.clientSecret)
      val headers = Map(
        "Authorization" -> show"Basic $credentials",
        "Content-Type"  -> "application/x-www-form-urlencoded; charset=UTF-8"
      )

      val uri = req.apiTokenUri.show
      workaroundService
        .post(uri, formBody, headers)
        .flatMap(decode[AccessTokenResponse](_).pipe(IO.fromEither))

    override def callbackError(req: CallbackErrorRequest): IO[GenericError] =
      GenericError.make(req.query.error).pure
  end apply

  def makeAuthorizeUri(req: AuthorizeRequest): Either[RefinementError, UriString] =
    val query = req.query
    Iterator(
      query.clientId.show.some.map("client_id" -> _),
      query.responseType.show.some.map("response_type" -> _),
      query.redirectUri.show.some.map("redirect_uri" -> _),
      query.showDialog.map(_.show.pipe("show_dialog" -> _)),
      query.state.map(_.show.pipe("state" -> _)),
      query.scope.map(_.mkString_(" ").pipe("scope" -> _))
    )
      .flatten
      .map((k, v) => show"$k=${WorkaroundService.encode(v)}")
      .toList
      .mkString_(show"${req.authorizeUri}?", "&", "")
      .refineE
