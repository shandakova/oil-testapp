package controllers

import play.api.mvc._

import javax.inject._


@Singleton
class SwaggerController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def redirectDocs: Action[AnyContent] = Action {
    Redirect(
      url = "/assets/lib/swagger-ui/index.html",
      queryStringParams = Map("url" -> Seq("/swagger.json")))
  }
}
