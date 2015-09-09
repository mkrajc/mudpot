package org.mudpot.engine.cmd

import java.util.Date

import org.mudpot.engine.{Command, SimpleEngine}
import org.mudpot.game.text.StopWordTokenProcessor
import org.mudpot.text.pattern.PatternEvaluator
import org.mudpot.text.pattern.exp.{ExpressionPattern, Placeholder, Word}


class DateCommand extends Command {
  override def name: String = "date"

  override def pattern: String = ???

  override def execute(args: List[String]): String = new Date().toString
}

object DateCommand {
  def main(args: Array[String]) {
    val stop = new StopWordTokenProcessor(List("the"))
    val datePat = new ExpressionPattern(List(Word("date"), Placeholder("")))
    val engine = new SimpleEngine(List(stop), new PatternEvaluator(List(datePat)))
    engine.addCommand(new DateCommand)
    println(engine.handle("date engine"))
    println(engine.handle(""))
    println(engine.handle(""))
    println(engine.handle("giberish"))
    println(engine.handle(""))
    println(engine.handle(""))
  }
}
