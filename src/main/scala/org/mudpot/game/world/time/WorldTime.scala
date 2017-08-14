package org.mudpot.game.world.time

import org.mudpot.game.world.time.WorldTime.DateParsers
import org.mudpot.text.Tokenizer

import scala.collection.immutable.IndexedSeq
import scala.collection.mutable.ListBuffer
import scala.util.parsing.combinator.JavaTokenParsers

object WorldTime {

  case class Date(time: Time, monthIndex: Int, year: Int)

  case class Time(hour: Int, minutes: Int) {
    override def toString: String = s"$hour:$minutes"
  }

  trait DateFormatter {
    def format(date: Date): String

    def parse(string: String): Date
  }

  //
  abstract class AbstractDateFormatter(format: String) extends DateFormatter {


    override def format(date: Date): String = {
      /*
      format.replace(MINUTES_FORMAT.toString, date.time.minutes.toString)
        .replace(HOUR_FORMAT.toString, date.time.hour.toString)
        .replace(YEAR_FORMAT.toString, date.year.toString)
        .replace(MONTH_FORMAT.toString, formatMonth(date.monthIndex))
        */
      ""
    }

    protected def formatMonth(idx: Int): String

    override def parse(string: String): Date = {


      //println(buf.toList.mkString(","))
      null
    }
  }

  class DateParsers extends JavaTokenParsers {

    val MONTH_FORMAT = 'M'
    val YEAR_FORMAT = 'Y'
    val HOUR_FORMAT = 'H'
    val MINUTES_FORMAT = 'm'

    val sep = ":"

    def MONTH: Parser[Int] = INTEGER

    def INTEGER: Parser[Int] = wholeNumber ^^ { i => i.toInt }
    def WORD: Parser[String] =  """\b""".r

    val FORMAT = (INTEGER ~ sep ~ INTEGER ~ MONTH ~ INTEGER) ^^ {
      case a ~ sep ~ b ~ month ~ year => Date(Time(a, b), month, year)
    }

    val M = "M".?
    val Y = "Y".?
    val m = "m".?
    val H = "H".?
    val others = """[^MYmH]*""".r

    def createFormat(tokens: List[String]): Unit = {}

    override def skipWhitespace: Boolean = true

    def handle(string: String): Parser[Any] = {
      def checkIndex(ch: Char, string: String): Int = {
        val occurrences: IndexedSeq[(Char, Int)] = string.zipWithIndex.filter(p => p._1 == ch)
        if (occurrences.size > 1) {
          throw new IllegalArgumentException(s"More instances of '$ch'")
        }
        string.indexOf(ch) + 1
      }

      val buf = new ListBuffer[Parser[_]]
      val ar = Array(MONTH_FORMAT, HOUR_FORMAT, MINUTES_FORMAT, YEAR_FORMAT)

      val ind = ar.map(checkIndex(_, string))

      val indexes: Array[Int] = ind.filterNot(_ <= 0)
      val res = indexes.sorted.foldLeft(new ListBuffer[String], 0)((p, idx) => {
        p._1 += string.substring(p._2, scala.math.min(idx, string.length))
        (p._1, idx)
      })

      val i = Tokenizer.tokenize(string).map(s => {
        if (s.length == 1 && ar.contains(s.charAt(0))) {
          INTEGER
        } else {
          WORD
        }
      })






      /*
      ar.foldLeft(List(string))((p, ch) => {
        val i = checkAndReplace(ch, p.mkString)
        p.mkString.splitAt(i)
      })
      */


      i.reduceLeft((current, next) => current ~ next)

    }


  }

  def main(args: Array[String]) {

    println(TestDateParsers.parseAll(TestDateParsers.FORMAT, "12 : 40 12 2015"))
    val a = new AbstractDateFormatter("") {
      override protected def formatMonth(idx: Int): String = ???
    }
    a.parse("M Y H::m X")

    val p: TestDateParsers.Parser[Any] = TestDateParsers.handle("M Y asdf")
    println(TestDateParsers.parseAll(p, "12 45 asdf"))


  }

}

object TestDateParsers extends DateParsers {

}




