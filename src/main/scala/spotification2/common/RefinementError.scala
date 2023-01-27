package spotification2.common

import cats.Show

final case class RefinementError(error: String) extends Exception(error)
object RefinementError:
  given Show[RefinementError] = Show.show(_.error)
