package spotification2.follow

import eu.timepit.refined.generic.Equal
import eu.timepit.refined.api.Refined
import spotification2.common.syntax.refined.*

// currently only `artist` is supported by Spotify.
type FollowTypeArtist = "artist"
type FollowTypeP = Equal[FollowTypeArtist]
type FollowType = String Refined FollowTypeP
object FollowType:
  val Artist: FollowType = "artist".refineU
