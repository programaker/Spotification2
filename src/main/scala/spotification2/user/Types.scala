package spotification2.user

import spotification2.common.Opaque
import spotification2.common.SpotifyId
import spotification2.common.UriString

object UserId extends Opaque[SpotifyId]
type UserId = UserId.OpaqueType

object UserApiUri extends Opaque[UriString]
type UserApiUri = UserApiUri.OpaqueType
