package spotification2.playlist

import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.And
import eu.timepit.refined.collection.MaxSize
import eu.timepit.refined.collection.MinSize

import spotification2.common.Opaque
import spotification2.common.SpotifyId
import spotification2.common.UriString

// A maximum of 100 Tracks can be processed in a single request
// An IndexedSeq is being used due to efficient `length` operation (needed for the refinement)
type PlaylistItemsToProcessMax = 100
type PlaylistItemsToProcessP = MinSize[1] And MaxSize[PlaylistItemsToProcessMax]
type PlaylistItemsToProcess[A] = Vector[A] Refined PlaylistItemsToProcessP
object PlaylistItemsToProcess:
  val MaxSize: PlaylistItemsToProcessMax = valueOf[PlaylistItemsToProcessMax]

object PlaylistId extends Opaque[SpotifyId]
type PlaylistId = PlaylistId.OpaqueType

object PlaylistApiUri extends Opaque[UriString]
type PlaylistApiUri = PlaylistApiUri.OpaqueType
