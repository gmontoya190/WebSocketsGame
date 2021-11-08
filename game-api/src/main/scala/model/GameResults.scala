package model

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

case class GameResults(messageType: String, results: List[Result])

case class PlayerResult(playerId: String, result: BigInt, number: BigInt)

case class Result(position: BigInt, playerID: String, number: BigInt, result: BigInt)

object Result {
  implicit val resultsCodec: Codec[Result] = deriveCodec
}

object GameResults {

  implicit val gameResultsCodec: Codec[GameResults] = deriveCodec

  def apply(result: List[Result]): GameResults = {
    new GameResults("response.results", result)
  }
}





