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
  def makeAuthorizeUri(req: AuthorizeRequest): Either[RefinementError, UriString] =
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
      .map((k, v) => show"$k=${encode(v)}")
      .toList
      .foldSmash(show"${req.authorizeUri}?", "&", "")
      .refineE
