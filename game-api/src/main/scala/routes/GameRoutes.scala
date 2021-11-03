package routes

import java.io

import cats.effect.std.Queue
import environment.GameRuntime
import error.{DomainError, NotRecognizedMessageType}
import repository.GameRepository
import repository.GameRepository.GameRepository
import zio.{IO, Task, ZIO}
//import cats.effect.{Async, ContextShift, Sync}
import fs2.concurrent.Topic
import fs2.{Pipe, Stream}
import model.{GameRequest, GameResults, InputMessage}
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import org.http4s.server.websocket.{WebSocketBuilder2}
import org.http4s.websocket.WebSocketFrame.{Close, Text}
import cats.effect._
import cats.effect.std.Queue
import cats.syntax.all._
import org.http4s.dsl.Http4sDsl
import org.http4s.websocket.WebSocketFrame



class GameRoutes[F[_]] (wsb: WebSocketBuilder2[F])
                       (implicit F: Async[F])
extends  Http4sDsl[F] with CirceJsonSupport {

def routes(gameRepository: GameRepository.Service): HttpRoutes[F] = {

  HttpRoutes.of[F] {
    case GET -> Root / "play" =>
      val getGameResults: Pipe[F, WebSocketFrame, WebSocketFrame] =
        _.collect {
          case Text(msg, _) => {
            GameRequest.processInput(msg) match {
              case Right(response) => Text(response.toString)
              case Left(error) => Text(error.message.toString)
            }
          }
          case _ => Text("Not handled message")
        }

      Queue
        .unbounded[F, Option[WebSocketFrame]]
        .flatMap { q =>
          val d: Stream[F, WebSocketFrame] = Stream.fromQueueNoneTerminated(q).through(getGameResults)
          val e: Pipe[F, WebSocketFrame, Unit] = _.enqueueNoneTerminated(q)
          wsb.build(d, e)
        }
  }
}

  def processInput(text: String, gameRepository: GameRepository.Service): ZIO[Any, io.Serializable, GameResults] = {
    val result = for {
      request <- ZIO.fromEither(GameRequest.processInput(text))
      result <-  gameRepository.runGame(request)
    } yield result
     result
  }

}
