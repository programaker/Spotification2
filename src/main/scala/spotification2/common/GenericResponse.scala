package spotification2.common

import sttp.tapir.Schema
import cats.Show
import cats.syntax.show.*
import cats.syntax.either.*
import io.circe.Encoder
import io.circe.Json
import io.circe.syntax.*
import io.circe.Decoder

enum GenericResponse derives Schema:
  case Success(message: String)
  case Error(message: String)

object GenericResponse:
  def success[S: Show](success: S): GenericResponse = Success(success.show)
  def error[E: Show](error: E): GenericResponse = Error(error.show)

  given Encoder[GenericResponse] = Encoder.instance {
    case Success(message) => Json.obj("success" -> message.asJson)
    case Error(message)   => Json.obj("error" -> message.asJson)
  }

  given Decoder[GenericResponse] = Decoder.instance { cursor =>
    cursor
      .downField("success")
      .as[String]
      .map(GenericResponse.success)
      .orElse(cursor.downField("error").as[String].map(GenericResponse.error))
  }
