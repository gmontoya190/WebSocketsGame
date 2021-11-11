import context.GameAppContext
import model.GameRequest
import org.scalatest.flatspec.AnyFlatSpec
import repository.GameRepository
import service.{GameService, Random}

class GameTestSpec extends AnyFlatSpec {
  private val context = new GameAppContext
  val random = Random(10)
  private val gameRepo: GameRepository.Service = new GameService(context.gameConfig, random)

  "Game" should "return values" in {

    val gameRequest = GameRequest("game", 3)
    val results = gameRepo.runGame(gameRequest)
    assert(results.size > 0)

  }
}