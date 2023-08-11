package spotification2.app.http

import cats.effect.IO
import org.http4s.blaze.server.BlazeServerBuilder

object HttpServer:
  def make = 
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      //.withHttpApp(Router("/" -> helloWorldRoutes).orNotFound)
      .withHttpApp(???)
      .resource
