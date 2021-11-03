package service

import error.DomainError
import model.{ComputerPlayer, GameRequest, GameResults, HumanPlayer, Player}
import repository.GameRepository
import zio.{IO, ZIO}
import zio._

class GameService extends GameRepository.Service {

  override def runGame(gameRequest: GameRequest): Task[GameResults] = {
    for {
      humans <- createNumberPlayers(gameRequest.numberPlayers)
      computePlayer = computerPlayer
      results <- generateScoreForPlayers(List(), computePlayer)

    } yield results
    UIO.succeed(GameResults("test", List()))
  }

  private def createNumberPlayers(number: Long): List[HumanPlayer] = {
    /// N = 10
    val humanPlayers: List[HumanPlayer]= List.range(1, number-1).map(_ => new HumanPlayer((new util.Random).nextInt(999)))
    humanPlayers
  }

  private def computerPlayer: ComputerPlayer = {
    new ComputerPlayer((new util.Random).nextInt(999))
  }
  private def generateScoreForPlayers(humanPlayers: List[HumanPlayer], computerPlayer: ComputerPlayer):  List[Player] = {
    val computerPlayers = List(computerPlayer)
    val mixList = humanPlayers ++ computerPlayers
    mixList
  }
}
