import model.GameRequest
import org.scalatest.flatspec.AnyFlatSpec
import repository.GameRepository
import service.GameService

class GameTestSpec extends AnyFlatSpec {

  private val gameRepo: GameRepository.Service = new GameService

  "Game" should "return values" in {

    val gameRequest = GameRequest("game", 3)
    val results = gameRepo.runGame(gameRequest)
    assert(results.size > 0)

  }
}