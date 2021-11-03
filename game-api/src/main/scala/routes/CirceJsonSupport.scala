package routes

import akka.http.scaladsl.server.directives.MarshallingDirectives
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import io.circe.Printer
import io.circe.generic.extras.{AutoDerivation, Configuration}

trait CirceJsonSupport extends ErrorAccumulatingCirceSupport with AutoDerivation with MarshallingDirectives {
  implicit val circeConfig: Configuration = Configuration.default.withStrictDecoding
  implicit val circePrinter: Printer = Printer.noSpaces.copy(dropNullValues = true)
}
