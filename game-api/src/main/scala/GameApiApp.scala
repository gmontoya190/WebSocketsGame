package main.scala

import cats.effect._
import context.GameAppContext
import fs2._
import org.http4s.blaze.server.BlazeServerBuilder
import repository.GameRepository
import routes.GameRoutes
import service.{GameService, Random}

object GameApiApp extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val context = new GameAppContext
    val random = Random(context.gameConfig.maxRandomGenerator)
    WebSocketApp[IO](context, random).stream.compile.drain.as(ExitCode.Success)
  }
}
class WebSocketApp[F[_]](gameContext: GameAppContext, random: Random)(implicit F: Async[IO]) {

  private val gameRepo: GameRepository.Service = new GameService(gameContext.gameConfig, random)

  def stream: Stream[IO, ExitCode] =
  BlazeServerBuilder[IO]
    .bindHttp(gameContext.serverConfig.port)
    .withHttpWebSocketApp(new GameRoutes[IO](_).routes(gameRepo).orNotFound)
    .serve
}

object WebSocketApp {
  def apply[F[_]: Async](gameContext: GameAppContext, random: Random): WebSocketApp[IO] =
    new WebSocketApp[IO](gameContext, random)
}


