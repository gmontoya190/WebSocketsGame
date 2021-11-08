package service

import model.{ComputerPlayer, GameRequest, HumanPlayer, Player, PlayerResult, Result}
import repository.GameRepository
class GameService extends GameRepository.Service {

  override def runGame(gameRequest: GameRequest): List[Result] = {
    val results = generateScoreForPlayers(createNumberPlayers(gameRequest.players), computerPlayer)
    results
  }

  private def createNumberPlayers(number: Long): List[HumanPlayer] = {
    /// N = 10
    val humanPlayers: List[HumanPlayer] = List.range(1, number).map(_ => new HumanPlayer(number.toString, (new util.Random).nextInt(999999).toString))
    humanPlayers
  }

  private def computerPlayer: List[ComputerPlayer] = {
    val computerPlayer = List(new ComputerPlayer("1", (new util.Random).nextInt(999999).toString))
    computerPlayer
  }
  private def generateScoreForPlayers(humanPlayers: List[HumanPlayer], computerPlayer: List[ComputerPlayer]):  List[Result] = {
    val playerResultComputed: List[PlayerResult] = computePlayers(computerPlayer)
    val playerResultHuman: List[PlayerResult] = computePlayers(humanPlayers)
    // list with results ordered
    val mixList: List[PlayerResult] = playerResultComputed ++ playerResultHuman

    val results: List[Result] = mixList.sortWith(_.result > _.result).zipWithIndex.map {
      case (player, position) => {
        new Result(position + 1, player.playerId, player.number, player.result)
      }
    }
    results
  }

  private def computePlayers(listPlayers: List[Player]): List[PlayerResult] = {

    val listPlayerResult: List[PlayerResult] = listPlayers.map{ player =>
      // Map ("2" -> 2, "3" -> "2")
      player match {
        case HumanPlayer(id, score) => {
          val resultPerDigit: Map[Char, BigDecimal] = player.asInstanceOf[HumanPlayer].score.groupBy(el => el).map(e => (e._1, e._2.length)) map {
            // Sum ("2" -> 99003, "3" -> 99003)
            case (number, times) => {
              (number, BigDecimal((scala.math.pow(10, times-1)) * number.asDigit))
            }
          }
          // List PlayerResults
          PlayerResult(id, resultPerDigit.foldLeft(BigInt(0))(_+_._2.toBigInt), BigInt(score))
        }
        case ComputerPlayer(id, score) => {
          val resultPerDigit: Map[Char, BigDecimal] = player.asInstanceOf[ComputerPlayer].score.groupBy(el => el).map(e => (e._1, e._2.length)) map {
            // Sum ("2" -> 99003, "3" -> 99003)
            case (number, times) => (number, BigDecimal((scala.math.pow(10, times-1)) * number.asDigit))
          }
          // List PlayerResults
          PlayerResult(id, resultPerDigit.foldLeft(BigInt(0))(_+_._2.toBigInt), BigInt(score))
        }
      }
    }
    listPlayerResult
  }
}
