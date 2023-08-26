package services

import model.BoundsOilPrice
import java.time.LocalDate

trait OilPriceService {
  def getPriceByDay(date: LocalDate): Option[BigDecimal]

  def getAveragePrice(startDate: LocalDate, endDate: LocalDate): Option[BigDecimal]

  def getPriceBound(startDate: LocalDate, endDate: LocalDate):  Option[BoundsOilPrice]

  def getStatistic(): BigInt

}
