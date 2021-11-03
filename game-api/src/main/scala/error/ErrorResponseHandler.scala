package error

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import routes.ErrorMapper

case class ErrorResponseBody(code: String, message: String)

object ErrorResponseHandler extends JsonSupport {


  implicit val game: ErrorMapper[DomainError] = {

    case error: InvalidMessageType =>
      HttpResponse(
        StatusCodes.BadRequest,
        entity = ErrorResponseBody(
          code = "error",
          error.message
        )
      )

    //Default case
      case error =>
      HttpResponse(
        StatusCodes.InternalServerError,
        entity = ErrorResponseBody("internal-error", error.message)
      )
  }

}
