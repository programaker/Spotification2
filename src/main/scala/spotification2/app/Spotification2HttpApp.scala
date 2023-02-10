package spotification2.app

import zio.ZIOAppDefault
import zio.Scope
import zio.ZIO
import zio.ZIOAppArgs

object Spotification2HttpApp extends ZIOAppDefault:
  override def run: ZIO[Any & ZIOAppArgs & Scope, Any, Any] = ???
