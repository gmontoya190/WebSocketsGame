package repository


import model.{GameRequest, Ping, Result}

object GameRepository {

  trait Service {
    def runGame(gameRequest: GameRequest): List[Result]
    def processPing(pingRequest: Ping): Long
  }

}
