package org.mudpot.engine.cmd

import java.util.Date

import org.mudpot.engine.{Command, SimpleEngine}


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
    println(engine.handle("date engine"))
    println(engine.handle(""))
    println(engine.handle(""))
    println(engine.handle("giberish"))
    println(engine.handle(""))
    println(engine.handle(""))
  }
}
