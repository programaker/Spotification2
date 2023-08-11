package spotification2.common

import io.circe.Codec
import sttp.tapir.Schema
import cats.Show
import cats.syntax.show.*

enum GenericResponse derives Codec.AsObject, Schema:
  case Success(success: String)
  case Error(error: String)

object GenericResponse:
  def success[S: Show](success: S): GenericResponse = Success(success.show)
  def error[E: Show](error: E): GenericResponse = Error(error.show)
