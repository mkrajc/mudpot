package org.mudpot.engine.cmd

import java.util.Date

import org.mudpot.engine.{SimpleEngine, Command}


class DateCommand extends Command {
  override def name: String = "date"

  override def aliases: Array[String] = ???

  override def pattern: String = ???

  override def execute(args: List[String]): String = new Date().toString
}

object DateCommand {
  def main(args: Array[String]) {
    val engine = new SimpleEngine
    engine.addCommand(new DateCommand)
    println(engine.handle("date"))
  }
}
