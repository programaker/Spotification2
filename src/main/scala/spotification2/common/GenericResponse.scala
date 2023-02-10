package spotification2.common

import cats.Show

enum GenericResponse:
  case Success[S: Show](success: S)
  case Error[E: Show](error: E)
