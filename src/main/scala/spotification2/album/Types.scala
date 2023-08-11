package spotification2.album

import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.Or
import eu.timepit.refined.generic.Equal
import eu.timepit.refined.string.MatchesRegex
import spotification2.common.Opaque
import spotification2.common.SpotifyId
import spotification2.common.UriString
import spotification2.common.syntax.refined.*

type AlbumTypeP = Equal["album"] Or Equal["single"] Or Equal["compilation"]
type AlbumType = String Refined AlbumTypeP
object AlbumType:
  val Album: AlbumType = "album".refineU

type ReleaseDateStringP = MatchesRegex["""^\d{4}(-\d{2})?(-\d{2})?$"""]
type ReleaseDateString = String Refined ReleaseDateStringP

type ReleaseDatePrecisionP = Equal["year"] Or Equal["month"] Or Equal["day"]
type ReleaseDatePrecision = String Refined ReleaseDatePrecisionP
object ReleaseDatePrecision:
  val Day: ReleaseDatePrecision = "day".refineU

type AlbumTrackSampleLimitValue = 1
type AlbumTrackSampleLimitP = Equal[AlbumTrackSampleLimitValue]
type AlbumTrackSampleLimit = Int Refined AlbumTrackSampleLimitP
object AlbumTrackSampleLimit:
  val Value: AlbumTrackSampleLimit = valueOf[AlbumTrackSampleLimitValue].refineU

object AlbumId extends Opaque[SpotifyId]
type AlbumId = AlbumId.OpaqueType

object AlbumApiUri extends Opaque[UriString]
type AlbumApiUri = AlbumApiUri.OpaqueType
