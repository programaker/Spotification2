package spotification2.common

import cats.Show
import io.circe.Codec

enum GenericResponse derives Codec.AsObject:
  case Success(success: String)
  case Error(error: String)
