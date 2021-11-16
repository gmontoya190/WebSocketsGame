package model

import io.circe.Codec
import io.circe.generic.semiauto.{deriveCodec, deriveEncoder}

case class GameResults(message_type: String, results: List[Result])

case class PlayerResult(playerId: String, result: BigInt, number: BigInt)

case class Result(position: BigInt, playerID: String, number: BigInt, result: BigInt)

case class Pong(message_type: String, request_id: BigInt, request_at: Long, timestamp: Long) {
}

object Result {
  implicit val resultsCodec: Codec[Result] = deriveCodec
}

object GameResults {

  implicit val gameResultsCodec: Codec[GameResults] = deriveCodec

  def apply(result: List[Result]): GameResults = {
    new GameResults("response.results", result)
  }
}

object Pong {
  import io.circe._, io.circe.generic.semiauto._
  implicit val invalidMessageTypeCodec: Encoder[Pong] = deriveEncoder[Pong]

  def apply(requestId: BigInt, request_at: Long, timeStamp: Long): Pong = {
    new Pong("response.pong", requestId, request_at, timeStamp)
  }
}





