package main.scala

import cats.effect._
import fs2._
import org.http4s.blaze.server.BlazeServerBuilder
import repository.GameRepository
import routes.GameRoutes
import service.GameService

object GameApiApp extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    BlazeWebSocketExampleApp[IO].stream.compile.drain.as(ExitCode.Success)
}
class BlazeWebSocketExampleApp[F[_]](implicit F: Async[F]) {
  private val gameRepo: GameRepository.Service = new GameService

  def stream: Stream[F, ExitCode] =
  BlazeServerBuilder[F]
    .bindHttp(8080)
    .withHttpWebSocketApp(new GameRoutes[F](_).routes(gameRepo).orNotFound)
    .serve
}

object BlazeWebSocketExampleApp {
  def apply[F[_]: Async]: BlazeWebSocketExampleApp[F] =
    new BlazeWebSocketExampleApp[F]
}


