package controllers

import io.swagger.annotations._
import play.api.libs.json.Json
import play.api.mvc._
import services.impl.FileLoadServiceImpl

import javax.inject._


@Api(value = "FileUpdateController")
@Singleton
class FileUpdateController @Inject()(val controllerComponents: ControllerComponents, private val fileLoadService: FileLoadServiceImpl) extends BaseController {

  @ApiOperation(
    value = "Обновить загруженные данные"
  )
  @ApiResponses(value = Array(
    new ApiResponse(code = 200, message = "Обновление успешно")
  ))
  def updateData() = Action {
    Ok(Json.toJson(fileLoadService.updateDb()))
  }

}
