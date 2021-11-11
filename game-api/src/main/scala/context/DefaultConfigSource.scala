package context

import pureconfig.{ConfigObjectSource, ConfigSource}

trait DefaultConfigSource {
  def configSource: ConfigObjectSource = ConfigSource.default
}
