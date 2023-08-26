package dao


import model.{BoundsOilPrice, OilPrice}
import play.api.db._
import anorm._
import anorm.SqlParser._

import java.time.LocalDate
import javax.inject.{Inject, Singleton}
import scala.collection.mutable.ListBuffer
import scala.language.postfixOps

@Singleton
class OilPriceDao @Inject()(db: Database) {
  def findAllCount(): Long = db.withConnection { implicit c =>
    SQL("select COUNT(*) from oil_price").as(scalar[Long].single)
  }

  def findAvgByPeriod(startDate: LocalDate, endDate: LocalDate): Option[BigDecimal] = db.withConnection { implicit c =>
    SQL("select avg(price) from oil_price where price_date > {start_date} and price_date < {end_date} ")
      .on(
        NamedParameter("start_date", startDate),
        NamedParameter("end_date", endDate)
      ).as(scalar[BigDecimal].singleOpt)
  }

  def findBoundsByPeriod(startDate: LocalDate, endDate: LocalDate): Option[BoundsOilPrice] = db.withConnection { implicit c =>
    SQL("select min(price) as min_price, max(price) as max_price from oil_price " +
      "where price_date > {start_date} and price_date < {end_date}")
      .on(
        NamedParameter("start_date", startDate),
        NamedParameter("end_date", endDate)
      ).as(boundsOilPrice.singleOpt).get
  }

  def findPriceByData(date: LocalDate): Option[BigDecimal] = db.withConnection { implicit c =>
    SQL("select price from oil_price " +
      "where price_date = {date} LIMIT 1")
      .on(
        NamedParameter("date", date)
      ).as(scalar[BigDecimal].singleOpt)
  }

  def savePriceList(list: ListBuffer[OilPrice]): Unit = {
    val indexedValues = list.toSeq.zipWithIndex
    val rows = indexedValues.map { case (_, i) =>
      s"({price_${i}}, {price_date_${i}})"
    }.mkString(",")
    val parameters = indexedValues.flatMap { case (value, i) =>
      Seq(
        NamedParameter(s"price_${i}", value.price),
        NamedParameter(s"price_date_${i}", value.date)
      )
    }
    db.withConnection { implicit c =>
      SQL("insert into oil_price (price, price_date) " +
        "values " + rows + " on conflict (price,price_date) do update SET price = EXCLUDED.price;")
        .on(parameters: _*).execute()

    }
  }


  private val boundsOilPrice: RowParser[Option[BoundsOilPrice]] = RowParser[Option[BoundsOilPrice]] {
    get[Option[BigDecimal]]("min_price") ~
      get[Option[BigDecimal]]("max_price") map {
      case minPrice ~ maxPrice =>
        if (minPrice.isEmpty || maxPrice.isEmpty) {
          None
        } else {
          Some(BoundsOilPrice(minPrice.get, maxPrice.get))
        }
    }
  }

}