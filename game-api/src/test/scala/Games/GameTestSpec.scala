import context.GameAppContext
import model.{GameRequest, Result}
import org.scalatest.flatspec.AnyFlatSpec
import repository.GameRepository
import org.mockito.scalatest.MockitoSugar
import org.scalatest.matchers.should.Matchers
import service.{GameService, Random}

class GameTestSpec extends AnyFlatSpec with MockitoSugar with Matchers {
  private val context = new GameAppContext
  val service = mock[Random]

  private val gameRepo: GameRepository.Service = new GameService(context.gameConfig, service)

  "Game" should "return values in the expected way when bigger number is the first one" in {
    when(service.nextInt)
      .thenReturn(999999)
      .andThen(546006)
      .andThen(576890)

    val gameRequest = GameRequest("game", 3)
    val results = gameRepo.runGame(gameRequest)
    val expectedResults = List (Result (1, "1", 999999, 900000), Result (2, "2", 546006, 69), Result (3, "3", 576890, 35))

    results.size should equal(expectedResults.size)
    results should equal(expectedResults)
  }

  "Game" should "return values in the expected way when bigger number is last one" in {
    when(service.nextInt)
      .thenReturn(111111)
      .andThen(546006)
      .andThen(576890)
      .andThen(999999)

    val gameRequest = GameRequest("game", 4)
    val results = gameRepo.runGame(gameRequest)
    val expectedResults = List (Result (1, "4", 999999, 900000), Result (2, "1", 111111, 100000),
      Result (3, "2", 546006, 69), Result (4, "3", 576890, 35))

    results.size should equal(expectedResults.size)
    results should equal(expectedResults)
  }

  "Game" should "return values should return empty list when no players" in {

    val gameRequest = GameRequest("game", 0)
    val results = gameRepo.runGame(gameRequest)
    results.size should equal (0)
  }

  "Game" should "return values should return empty list when number players in negative values" in {
    val gameRequest = GameRequest("game", -1)
    val results = gameRepo.runGame(gameRequest)
    results.size should equal (0)
  }

}