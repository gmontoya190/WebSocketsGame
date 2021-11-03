package environment

import repository.GameRepository
import service.GameService
import zio.Has
import zio.internal.Platform


class LiveGameRuntime extends zio.Runtime[GameRuntime] {

  private val gameRepository: GameRepository.Service = new GameService

  override val environment: GameRuntime = Has(gameRepository)

  override val platform: Platform =
    Platform.makeDefault().withReportFailure(_ => ())
}
