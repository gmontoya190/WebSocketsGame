package service

import java.time.{Instant, LocalDateTime}

import config.GameConfig
import model.{ComputerPlayer, GameRequest, HumanPlayer, Ping, Player, PlayerResult, Result}
import repository.GameRepository
class GameService(gameContext: GameConfig, random: Random) extends GameRepository.Service {

  override def runGame(gameRequest: GameRequest): List[Result] = {

    val totalPlayers = gameRequest.players
    if (totalPlayers > 0) {
      val numberHumanPlayers = gameRequest.players - gameContext.numberComputePlayers

      val humanPlayers = createNumberPlayers(numberHumanPlayers)
      val computerPlayers = createComputerPlayer(numberHumanPlayers, totalPlayers)
      val results = generateScoreForPlayers(humanPlayers, computerPlayers)
      results
    } else {
      List ()
    }
  }

  override def processPing(pingRequest: Ping): Long = {
     Instant.now().getEpochSecond
  }

  private def createNumberPlayers(numberPlayers: Int): List[HumanPlayer] = {
    val humanPlayers: List[HumanPlayer] = List.range(1, numberPlayers +1)
      .map(index => new HumanPlayer(index.toString,  (random.nextInt.toString)))
    humanPlayers
  }

  private def createComputerPlayer(humanPlayers: Int, totalPlayers: Int): List[ComputerPlayer] = {
    val computerPlayer = List.range(humanPlayers +1, totalPlayers +1)
      .map(index => new ComputerPlayer(index.toString, (random.nextInt.toString)))
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
        case player:Player => {
          val resultPerDigit: Map[Char, BigDecimal] = player.score.groupBy(el => el).map(e => (e._1, e._2.length)) map {
            // Sum ("2" -> 99003, "3" -> 99003)
            case (number, times) => {
              (number, BigDecimal((scala.math.pow(10, times-1)) * number.asDigit))
            }
          }
          // List PlayerResults
          PlayerResult(player.id, resultPerDigit.foldLeft(BigInt(0))(_+_._2.toBigInt), BigInt(player.score))
        }
      }
    }
    listPlayerResult
  }
}
