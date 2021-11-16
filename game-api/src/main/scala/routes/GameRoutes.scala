package routes


import repository.GameRepository
import fs2.{Pipe, Stream}
import model.{GameRequest, GameResults, InvalidMessageType, MessageType, Ping, Pong}
import org.http4s.HttpRoutes
import org.http4s.server.websocket.WebSocketBuilder2
import org.http4s.websocket.WebSocketFrame.Text
import cats.effect._
import cats.effect.std.Queue
import org.http4s.dsl.Http4sDsl
import org.http4s.websocket.WebSocketFrame
import io.circe.syntax._



class GameRoutes[F[_]] (wsb: WebSocketBuilder2[IO])
                       (implicit F: Async[IO])
extends  Http4sDsl[F] {

def routes(gameRepository: GameRepository.Service): HttpRoutes[IO] = {

  HttpRoutes.of[IO] {
    case GET -> Root / "play" =>
      val getGameResults: Pipe[IO, WebSocketFrame, WebSocketFrame] =
        _.collect {
          case Text(msg, _) => {
            MessageType.fromString(msg) match {
              case Right(game: GameRequest) => processGame(game, gameRepository)
              case Right(ping: Ping) => processPing(ping, gameRepository)
              case Left(error) => processError(error)

            }
          }
          case _ => Text("Not handled message")
        }

      Queue
        .unbounded[IO, Option[WebSocketFrame]]
        .flatMap { q =>
          val d: Stream[IO, WebSocketFrame] = Stream.fromQueueNoneTerminated(q).through(getGameResults)
          val e: Pipe[IO, WebSocketFrame, Unit] = _.enqueueNoneTerminated(q)
          wsb.build(d, e)
        }
  }
}
  def processGame(gameRequest: GameRequest, gameRepository: GameRepository.Service): Text = {
   Text(GameResults.apply(gameRepository.runGame(gameRequest)).asJson.noSpaces)
  }

  def processError(error: io.circe.Error): Text = {
    Text(InvalidMessageType("Unknown message format").asJson.noSpaces)
  }
  def processPing(pingRequest: Ping, gameRepository: GameRepository.Service): Text = {
    Text(Pong(pingRequest.id, pingRequest.timeStamp, gameRepository.processPing(pingRequest)).asJson.noSpaces)
  }

}
