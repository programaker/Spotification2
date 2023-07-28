package spotification2.common

import io.circe.Codec
import sttp.tapir.Schema

enum GenericResponse derives Codec.AsObject, Schema:
  case Success(success: String)
  case Error(error: String)
