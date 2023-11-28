package spotification2.common

import cats.Show
import cats.syntax.show.*
import io.circe.Decoder
import io.circe.Encoder
import sttp.tapir.Schema
import io.circe.derivation.ConfiguredCodec
import spotification2.common.json.SpotifyJSonConfig.given

final case class GenericSuccess(success: String) derives ConfiguredCodec, Schema
object GenericSuccess:
  def make[S: Show](success: S): GenericSuccess = GenericSuccess(success.show)

final case class GenericError(error: String) derives ConfiguredCodec, Schema
object GenericError:
  def make[S: Show](error: S): GenericError = GenericError(error.show)
