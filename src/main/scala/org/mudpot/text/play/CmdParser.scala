package org.mudpot.text.play

import java.util.Date

import org.mudpot.game.cmd.{XPureCommand, XCommand}
import org.mudpot.game.text.cmd.LookCommand
import org.mudpot.text.cmd.Command

import scala.language.postfixOps
import scala.util.parsing.combinator.RegexParsers

sealed trait Expression

sealed trait Obj extends Expression

case class Item(name: String) extends Obj

case object Unknown extends Obj


sealed trait CommandExpression extends Expression

case object Date extends CommandExpression

case class Look(obj: Obj) extends CommandExpression

object CmdParser extends RegexParsers {

  def identifier = """[_\p{L}][_\p{L}\p{Nd}]*""".r

  def item = identifier ^^ { s => Item(s) }

  def optItem: Parser[Obj] = (item ?) ^^ {
    case Some(o) => o
    case None => Unknown
  }

  def date = "date" ^^ { case _ => Date }

  def look = "look" ~> ("at" ?) ~> optItem ^^ { i => Look(i) }

  def root = look | date

  def apply(input: String): Option[CommandExpression] = parseAll(root, input) match {
    case Success(result, _) => Some(result)
    case NoSuccess(_, _) => None
  }
}

case object DateCommnad extends XPureCommand[Date]

object Test {
//  implicit def lookCommand(look: CommandExpression): XCommand = LookCommand
  implicit def dateCommand(look: Date): XPureCommand[Date] = DateCommnad

  def main(args: Array[String]) {
    println(CmdParser("look sun"))
    println(CmdParser("look"))
    println(CmdParser("look at"))
    val parser: Option[CommandExpression] = CmdParser("look at moon")
    println(parser)

    //val command: XCommand[Nothing,Date] = parser.get
    //println(command)
  }
}
