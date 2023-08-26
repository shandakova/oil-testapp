package services.impl

import dao.OilPriceDao
import model.BoundsOilPrice
import services.OilPriceService

import java.time.LocalDate
import javax.inject.{Inject, Singleton}

@Singleton
class OilPriceServiceImpl @Inject()(private val daoPrice: OilPriceDao) extends OilPriceService {

  override def getPriceByDay(date: LocalDate): Option[BigDecimal] = {
    daoPrice.findPriceByData(date)
  }

  override def getAveragePrice(startDate: LocalDate, endDate: LocalDate): Option[BigDecimal] = {
    daoPrice.findAvgByPeriod(startDate, endDate)
  }

  override def getPriceBound(startDate: LocalDate, endDate: LocalDate): Option[BoundsOilPrice] = {
    daoPrice.findBoundsByPeriod(startDate, endDate)
  }

  override def getStatistic(): BigInt = {
    daoPrice.findAllCount()
  }
}
