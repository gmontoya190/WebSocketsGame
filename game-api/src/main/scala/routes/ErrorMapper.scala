package routes

import akka.http.scaladsl.model.HttpResponse

trait ErrorMapper[E] {
  def toHttpResponse(e: E): HttpResponse
}
