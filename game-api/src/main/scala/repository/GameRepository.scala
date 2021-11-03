package repository

import error.DomainError
import model.{GameRequest, GameResults}
import zio.{Has, IO, ZIO}
import zio._

object GameRepository {

  type GameRepository = Has[GameRepository.Service]

  trait Service {
    def runGame(gameRequest: GameRequest): Task[GameResults]
  }

}
