package controllers

import model.OilPrice
import play.api.libs.json.Json
import play.api.mvc._

import java.time.{LocalDate, Period}
import javax.inject._
import scala.collection.mutable


@Singleton
class SwaggerController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def redirectDocs: Action[AnyContent] = Action {
    Redirect(
      url = "/assets/lib/swagger-ui/index.html",
      queryStringParams = Map("url" -> Seq("/swagger.json")))
  }
}
