package spotification2.config

import scala.concurrent.duration.FiniteDuration

import eu.timepit.refined.pureconfig.*
import pureconfig.ConfigReader

import spotification2.album.AlbumApiUri
import spotification2.artist.ArtistApiUri
import spotification2.auth.ApiTokenUri
import spotification2.auth.AuthorizeUri
import spotification2.auth.ClientId
import spotification2.auth.ClientSecret
import spotification2.auth.RedirectUri
import spotification2.auth.Scope
import spotification2.common.Host
import spotification2.common.PositiveInt
import spotification2.me.MeApiUri
import spotification2.playlist.PlaylistApiUri
import spotification2.track.TrackApiUri
import spotification2.user.UserApiUri

final case class AppConfig(
  authorization: AuthorizationConfig,
  playlist: PlaylistConfig,
  artist: ArtistConfig,
  album: AlbumConfig,
  track: TrackConfig,
  me: MeConfig,
  user: UserConfig,
  server: ServerConfig,
  client: ClientConfig
) derives ConfigReader

final case class AuthorizationConfig(
  clientId: ClientId,
  clientSecret: ClientSecret,
  redirectUri: RedirectUri,
  authorizeUri: AuthorizeUri,
  apiTokenUri: ApiTokenUri,
  scopes: Option[List[Scope]]
) derives ConfigReader

final case class PlaylistConfig(
  playlistApiUri: PlaylistApiUri,
  getPlaylistItemsLimit: PositiveInt,
  mergePlaylistsRetry: RetryConfig
) derives ConfigReader

final case class ArtistConfig(
  artistApiUri: ArtistApiUri
) derives ConfigReader

final case class AlbumConfig(
  albumApiUri: AlbumApiUri
) derives ConfigReader

final case class TrackConfig(
  trackApiUri: TrackApiUri
) derives ConfigReader

final case class MeConfig(
  meApiUri: MeApiUri
) derives ConfigReader

final case class UserConfig(
  userApiUri: UserApiUri
) derives ConfigReader

final case class ServerConfig(
  host: Host,
  port: PositiveInt
) derives ConfigReader

final case class ClientConfig(
  logHeaders: Boolean,
  logBody: Boolean
) derives ConfigReader

final case class RetryConfig(
  retryAfter: FiniteDuration,
  attempts: PositiveInt
) derives ConfigReader
