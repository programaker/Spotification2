package spotification2.track

import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.api.Refined
import spotification2.common.SpotifyId
import spotification2.common.Opaque
import spotification2.common.UriString

type TrackUriP = MatchesRegex["^spotify:track:[0-9a-zA-Z]+$"]
type TrackUri = String Refined TrackUriP

object TrackId extends Opaque[SpotifyId]
type TrackId = TrackId.OpaqueType

object TrackApiUri extends Opaque[UriString]
type TrackApiUri = TrackApiUri.OpaqueType
