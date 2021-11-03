package model

import error.{DomainError, NotRecognizedMessageType}
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import io.circe.parser._
import org.http4s.websocket.WebSocketFrame.Text

sealed trait InputMessage

case class GameRequest(messageType: String, numberPlayers: Long) extends InputMessage

case class Error(error: String) extends InputMessage

case class Disconnect(message: String) extends InputMessage




object GameRequest {

  implicit val transactionCodec: Codec[GameRequest] = deriveCodec

  def processInput(text: String): Either[DomainError, GameRequest] = {
    decode[GameRequest](text) match {
      case Right(gameRequest) => Right(gameRequest)
      case Left(error) => Left(NotRecognizedMessageType("error"))
    }
  }

  def Validate (text: String): Text = {
    decode[GameRequest](text) match {
      case Right(gameRequest) => Text("hola")
      case Left(error) => Text("unhandle Message")
    }
  }

}