package model

import error.{DomainError, NotRecognizedMessageType}
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import io.circe.parser._
import org.http4s.websocket.WebSocketFrame.Text

case class GameRequest(message_type: String, players: Long)

case class Error(error: String)

case class Disconnect(message: String)




object GameRequest {

  implicit val transactionCodec: Codec[GameRequest] = deriveCodec

  def processInput(text: String): Either[DomainError, GameRequest] = {
    decode[GameRequest](text) match {
      case Right(gameRequest) => Right(gameRequest)
      case Left(error) => Left(NotRecognizedMessageType("Message is not in the correct format"))
    }
  }

  def Validate (text: String): Text = {
    decode[GameRequest](text) match {
      case Right(gameRequest) => Text("hola")
      case Left(error) => Text("unhandle Message")
    }
  }

}