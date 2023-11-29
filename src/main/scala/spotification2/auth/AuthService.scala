package spotification2.auth

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import cats.syntax.all.*
import eu.timepit.refined.cats.*

import spotification2.common.RefinementError
import spotification2.common.UriString
import spotification2.common.syntax.refined.*

trait AuthService

object AuthService:
  def makeAuthorizeUri(authorizeUri: AuthorizeUri, req: AuthorizeRequest): Either[RefinementError, UriString] =
    // Sttp Uri should be able to encode query params, but in my tests
    // URIs are not properly encoded:
    //
    // val redirectUri = "https://bar.com"
    // uri"https://foo.com?redirect_uri=$redirectUri"
    // >>> Uri = https://foo.com?redirect_uri=https://bar.com <- did not encode `:` nor `//`
    //
    // URLEncoder.encode("https://bar.com", StandardCharsets.UTF_8)
    // >>> String = https%3A%2F%2Fbar.com <- encoded `:` and `//` correctly
    val encode = URLEncoder.encode(_: String, StandardCharsets.UTF_8)

    Iterator(
      req.clientId.show.some.map("client_id" -> _),
      req.responseType.show.some.map("response_type" -> _),
      req.redirectUri.show.some.map("redirect_uri" -> _),
      req.showDialog.map(_.show.pipe("show_dialog" -> _)),
      req.state.map(_.show.pipe("state" -> _)),
      req.scope.map(_.mkString_(" ").pipe("scope" -> _))
    )
      .flatten
      .map((k, v) => show"$k=${encode(v)}")
      .toList
      .foldSmash(show"$authorizeUri?", "&", "")
      .refineE
