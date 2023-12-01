package spotification2.common.api

import cats.effect.IO
import sttp.tapir.server.ServerEndpoint

trait ApiServerEndpoints:
  def apiServerEndpoints: List[ServerEndpoint[Any, IO]]
