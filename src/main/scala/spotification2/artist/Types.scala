package spotification2.artist

import eu.timepit.refined.generic.Equal
import eu.timepit.refined.boolean.Or
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.numeric.Interval
import spotification2.common.syntax.refined.*
import spotification2.common.Opaque
import spotification2.common.SpotifyId
import spotification2.common.UriString

type IncludeAlbumGroupP =
  Equal["album"] Or Equal["single"] Or Equal["appears_on"] Or Equal["compilation"]
type IncludeAlbumGroup = String Refined IncludeAlbumGroupP

type IncludeAlbumGroupsStringP = MatchesRegex["""^[a-z]([a-z_])+(\,([a-z_])+)*[a-z]$"""]
type IncludeAlbumGroupsString = String Refined IncludeAlbumGroupsStringP

type MyFollowedArtistsToProcessMax = 50
type MyFollowedArtistsLimitP = Interval.Closed[1, MyFollowedArtistsToProcessMax]
type MyFollowedArtistsLimit = Int Refined MyFollowedArtistsLimitP
object MyFollowedArtistsLimit:
  val MaxValue: MyFollowedArtistsLimit = valueOf[MyFollowedArtistsToProcessMax].refineU

type ArtistAlbumsToProcessMax = 50
type ArtistAlbumsLimitP = Interval.Closed[1, ArtistAlbumsToProcessMax]
type ArtistAlbumsLimit = Int Refined ArtistAlbumsLimitP
object ArtistAlbumsLimit:
  val MaxValue: ArtistAlbumsLimit = valueOf[ArtistAlbumsToProcessMax].refineU

object ArtistId extends Opaque[SpotifyId]
type ArtistId = ArtistId.OpaqueType

object ArtistApiUri extends Opaque[UriString]
type ArtistApiUri = ArtistApiUri.OpaqueType
