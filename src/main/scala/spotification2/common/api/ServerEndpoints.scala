package spotification2.common.api

import cats.effect.IO
import sttp.tapir.server.ServerEndpoint

trait MkServerEndpoint:
  def mkServerEndpoint: ServerEndpoint[Any, IO]

trait ListServerEndpoints:
  def serverEndpoints: List[ServerEndpoint[Any, IO]]  
