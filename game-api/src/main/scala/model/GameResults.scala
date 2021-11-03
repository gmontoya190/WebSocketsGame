package model

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

case class GameResults(messageType: String, results: List[Result])

case class Result(position: String, playerID: String, number: Long, result: Long)

object Result {
  implicit val resultsCodec: Codec[Result] = deriveCodec
}

object GameResults {
  implicit val gameResultsCodec: Codec[GameResults] = deriveCodec
}





