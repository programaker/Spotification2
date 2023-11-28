package spotification2.common.api

import sttp.tapir.server.ServerEndpoint
import cats.effect.IO

trait MkServerEndpoint:
  def mkServerEndpoint: ServerEndpoint[Any, IO]

trait ListServerEndpoints:
  def serverEndpoints: List[ServerEndpoint[Any, IO]]  
