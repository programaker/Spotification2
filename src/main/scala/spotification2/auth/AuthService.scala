package spotification2.auth

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import cats.syntax.all.*
import eu.timepit.refined.cats.*

import spotification2.common.RefinementError
import spotification2.common.UriString
import spotification2.common.syntax.refined.*
import cats.effect.IO
import java.util.Base64
import io.circe.parser.*
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers

trait AuthService:
  def requestAccessToken(req: AccessTokenRequest): IO[AccessTokenResponse]

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
end AuthService

///

// So, why does this Service exist?
//
// Sttp Uri should be able to encode query params, but in my tests URIs are not properly encoded:
//
// val redirectUri = "https://bar.com" uri"https://foo.com?redirect_uri=$redirectUri"
// >>> Uri = https://foo.com?redirect_uri=https://bar.com <- did not encode `:` nor `//`
//
// URLEncoder.encode("https://bar.com", StandardCharsets.UTF_8)
// >>> String = https%3A%2F%2Fbar.com <- encoded `:` and `//` correctly
//
// DISCLAIMER: when I say "properly" or "incorrectly", I mean in relation to what Spotify expects.
// The way Sttp does it is probably based on some specification that the Internet refuses to follow.
// Http4s had the same issue: https://github.com/http4s/http4s/issues/2445
//
// To deal with these problems, we had to resort to Java `URLEncoder` and `HttpClient`.
trait WorkaroundService:
  def post(uri: String, body: String, headers: Map[String, String]): IO[String]

object WorkaroundService:
  def apply(): WorkaroundService = new:
    override def post(uri: String, body: String, headers: Map[String, String]): IO[String] =
      val httpClient = IO {
        HttpClient
          .newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .build()
      }

      val request = IO {
        val reqBuilder = HttpRequest
          .newBuilder(URI.create(uri))
          .POST(BodyPublishers.ofString(body, StandardCharsets.UTF_8))

        val reqBuilder2 = headers.foldLeft(reqBuilder) { case (builder, (key, value)) =>
          builder.header(key, value)
        }

        reqBuilder2.build()
      }

      (httpClient, request).flatMapN { (httpClient, request) =>
        IO.fromCompletableFuture(IO(httpClient.sendAsync(request, BodyHandlers.ofString()))).map(_.body())
      }
  end apply

  def encode(s: String): String =
    URLEncoder.encode(s, StandardCharsets.UTF_8)

  def base64Credentials(clientId: ClientId, clientSecret: ClientSecret): String =
    val s = show"$clientId:$clientSecret"
    Base64.getEncoder.encodeToString(s.getBytes(StandardCharsets.UTF_8))
end WorkaroundService
