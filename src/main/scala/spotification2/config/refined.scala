package spotification2.config.refined

import cats.syntax.either.*
import eu.timepit.refined.api.Refined
import eu.timepit.refined.api.Validate
import spotification2.common.syntax.refined.*
import pureconfig.ConfigReader
import pureconfig.error.ExceptionThrown

// Only because Purecofig refined integration is not working anymore
// [UPDATE] Might not be necessary anymore
given [T, P](using cr: ConfigReader[T], v: Validate[T, P]): ConfigReader[Refined[T, P]] =
  cr.emap(_.refineE[P].leftMap(ExceptionThrown.apply))