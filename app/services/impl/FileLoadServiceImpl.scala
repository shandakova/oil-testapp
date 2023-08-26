package services.impl;

import com.github.tototoshi.csv._
import dao.OilPriceDao
import model.OilPrice
import services.FileLoadService

import java.io.File
import java.time.LocalDate
import java.time.format.{DateTimeFormatter, DateTimeParseException}
import javax.inject.{Inject, Singleton}
import scala.collection.mutable.ListBuffer
import scala.util.Using

@Singleton
class FileLoadServiceImpl @Inject()(val oilPriceDao: OilPriceDao) extends FileLoadService {

  def updateDb(): String = {
    val filePath: String = "conf/data/security.csv"
    fillDbByFile(filePath)
  }

  implicit object CsvFormat extends DefaultCSVFormat {
    override val delimiter = ';'
  }

  private val pathLoadSize = 100

  private def fillDbByFile(filePath: String): String = {
    Using(CSVReader.open(new File(filePath), "Windows-1251")) { reader =>
      val iter = reader.iterator
      val patch: ListBuffer[OilPrice] = new ListBuffer[OilPrice]
      var numLoad = 0;
      while (iter.hasNext) {
        val temp = iter.next()
        if (temp.length > 6) {
          try {
            patch += OilPrice(
              LocalDate.parse(temp(2), DateTimeFormatter.ofPattern("dd.MM.yyyy")),
              BigDecimal(temp(6).replace(",", "."))
            )
          } catch {
            case e: DateTimeParseException => println("not parse " + temp(2))
            case e: NumberFormatException => println("not parse " + temp(6))
          }
        }
        if (patch.length > pathLoadSize) {
          oilPriceDao.savePriceList(patch)
          numLoad += patch.length
          patch.clear()
        }
      }
      if (patch.nonEmpty) {
        oilPriceDao.savePriceList(patch)
        numLoad += patch.length
      }
      return s"Load ${numLoad} rows"
    }
    "Failed read file"
  }


}
