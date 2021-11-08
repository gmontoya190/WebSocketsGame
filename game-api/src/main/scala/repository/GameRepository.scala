package repository

import error.DomainError
import model.{GameRequest, GameResults, PlayerResult, Result}
import zio.{Has, IO, ZIO}
import zio._

object GameRepository {

  type GameRepository = Has[GameRepository.Service]

  trait Service {
    def runGame(gameRequest: GameRequest): List[Result]
  }

}
