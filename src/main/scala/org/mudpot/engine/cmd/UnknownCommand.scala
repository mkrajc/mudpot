package org.mudpot.engine.cmd

import org.mudpot.engine.Command

import scala.util.Random

object UnknownCommand extends Command {
  private val answers: List[String] = List(
    "The world does not understand me.",
    "Sometime my tongue is quicker than my mind.",
    "What I was trying to say?"
  )

  override def pattern: String = ???

  override def execute(args: List[String]): String = {
    answers.drop(Random.nextInt(answers.size)).head
  }

  override def name: String = "<unknown>"
}
