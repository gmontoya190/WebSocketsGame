package model

import io.circe.{Decoder, DecodingFailure, Error, HCursor}


sealed trait MessageType {
  def message_type: String
}

case class GameRequest(players: Int) extends MessageType {
  override val message_type = "request.play"
}

case class InvalidMessageType(message: String) extends MessageType {
  override val message_type = "invalid.message"
}
case class Ping(id: BigInt, timeStamp: Long) extends MessageType {
  override val message_type = "request.ping"
}

case class Pong(requestId: BigInt, request_at: Long, timeStamp: Long) extends MessageType {
  override val message_type = "response.pong"
}

object MessageType {

  import io.circe.generic.auto._

  implicit val messageTypeDecoder: Decoder[MessageType] = new Decoder[MessageType] {

    def apply(cursor: HCursor): Either[DecodingFailure, MessageType] =
      cursor.downField("message_type").as[String].flatMap {
        case "request.play" => cursor.as[GameRequest]
        case "request.ping" => cursor.as[Ping]
        case _ => cursor.as[InvalidMessageType]
      }
  }
  def fromString(s: String): Either[Error, MessageType] = {
    io.circe.parser.parse(s).flatMap(messageTypeDecoder.decodeJson)
  }

}
object InvalidMessageType {
  import io.circe._, io.circe.generic.semiauto._
  implicit val invalidMessageTypeCodec: Encoder[InvalidMessageType] = deriveEncoder[InvalidMessageType]
}

object Pong {
  import io.circe._, io.circe.generic.semiauto._
  implicit val invalidMessageTypeCodec: Encoder[Pong] = deriveEncoder[Pong]
}
