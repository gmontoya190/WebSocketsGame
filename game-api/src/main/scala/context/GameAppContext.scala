package context

import com.typesafe.scalalogging.LazyLogging
import config.{GameConfig, ServerConfig}

class GameAppContext extends DefaultConfigSource with LazyLogging {

 val serverConfig: ServerConfig = configSource.at("server").loadOrThrow[ServerConfig]
  logger.info(s"Config is: $serverConfig")

  val gameConfig: GameConfig = configSource.at("game").loadOrThrow[GameConfig]
 logger.info(s"game config: $gameConfig")
}
