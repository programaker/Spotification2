package spotification2.monitoring.api

import zio.http.Http
import zio.http.Request
import zio.http.model.Method.GET
import zio.http.*
import zio.http.Response

object HealthCheckApi:
  def endpoints: Http[Any, Nothing, Request, Response] = Http.collect[Request] { //
    case GET -> !! / "health" => Response.text("I'm doing well, thanks for asking ^_^")
  }
