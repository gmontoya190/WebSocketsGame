package config

import pureconfig.ConfigReader
import pureconfig.generic.semiauto.deriveReader

case class ServerConfig(
    host: String,
    port: Int
)
case class GameConfig(
    maxRandomGenerator: Int,
    numberComputePlayers: Int

                 )
object ServerConfig {
  implicit val sReader: ConfigReader[ServerConfig] = deriveReader[ServerConfig]
}
object GameConfig {
  implicit val gReader: ConfigReader[GameConfig] = deriveReader[GameConfig]
}