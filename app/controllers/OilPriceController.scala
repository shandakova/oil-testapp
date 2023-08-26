package controllers

import io.swagger.annotations.{Api, ApiOperation, ApiParam, ApiResponse, ApiResponses}
import model.BoundsOilPrice
import play.api.libs.json.Json
import play.api.mvc._
import services.impl.OilPriceServiceImpl

import java.time.LocalDate
import javax.inject._


@Api(value = "OilPriceController", produces = "application/json")
@Singleton
class OilPriceController @Inject()(val controllerComponents: ControllerComponents, private val oilPriceService: OilPriceServiceImpl) extends BaseController {
  implicit val boundsJson = Json.format[BoundsOilPrice]

  @ApiOperation(
    value = "Цена на заданную дату"
  )
  @ApiResponses(value = Array(
    new ApiResponse(code = 400, message = "Передан неверный формат даты"),
    new ApiResponse(code = 204, message = "Не найдена цена с такой датой"),
    new ApiResponse(code = 200, message = "Цена найдена")
  ))
  def getPriceByDay(@ApiParam(value = "Дата в формате YYYY-MM-dd") date: String) = Action {
    val reqDate = toLocalDate(date);
    if (reqDate.isEmpty) {
      BadRequest("Передана невалидная дата")
    } else {
      val founded = oilPriceService.getPriceByDay(reqDate.get)
      if (founded.isEmpty) {
        NoContent
      } else {
        Ok(founded.get.toString())
      }
    }
  }

  def toLocalDate(dateString: String): Option[LocalDate] = {
    try {
      Some(LocalDate.parse(dateString.trim))
    } catch {
      case e: Exception => None
    }
  }

  @ApiOperation(
    value = "Средняя цена за промежуток времени"
  )
  @ApiResponses(value = Array(
    new ApiResponse(code = 400, message = "Переданы неверные даты"),
    new ApiResponse(code = 204, message = "Не найдено цен в данном диапазоне"),
    new ApiResponse(code = 200, message = "Средняя цена вычислена успешно")
  ))
  def getAveragePrice(@ApiParam(value = "Дата начала периода в формате YYYY-MM-dd") startDate: String,
                      @ApiParam(value = "Дата конца периода в формате YYYY-MM-dd") endDate: String) = Action {
    val start = toLocalDate(startDate)
    val end = toLocalDate(endDate)
    if (start.isEmpty || end.isEmpty || start.get.isAfter(end.get)) {
      BadRequest("Даты не валидны или расположены хронологически неверно")
    } else {
      val average = oilPriceService.getAveragePrice(start.get, end.get);
      if (average.isEmpty) {
        NoContent
      } else {
        Ok(average.get.toString())
      }
    }
  }

  @ApiOperation(
    value = "Максимальная и минимальная цены за промежуток времени"
  )
  @ApiResponses(value = Array(
    new ApiResponse(code = 400, message = "Переданы неверные даты"),
    new ApiResponse(code = 204, message = "Не найдено цен в данном диапазоне"),
    new ApiResponse(code = 200, message = "Средняя цена вычислена успешно")
  ))
  def getPriceBound(@ApiParam(value = "Дата начала периода в формате YYYY-MM-dd") startDate: String,
                    @ApiParam(value = "Дата конца периода в формате YYYY-MM-dd") endDate: String) = Action {
    val start = toLocalDate(startDate)
    val end = toLocalDate(endDate)
    if (start.isEmpty || end.isEmpty || start.get.isAfter(end.get)) {
      BadRequest("Даты не валидны или расположены хронологически неверно")
    } else {
      val priceBound = oilPriceService.getPriceBound(start.get, end.get)
      if (priceBound.isEmpty) {
        NoContent
      } else {
        Ok(Json.toJson(priceBound))
      }
    }
  }


  @ApiOperation(
    value = "Статистика по загруженным данным"
  )
  @ApiResponses(value = Array(
    new ApiResponse(code = 200, message = "Статистика вычислена успешно")
  ))
  def getStatistic() = Action {
    Ok(Json.toJson(oilPriceService.getStatistic()))
  }

}
