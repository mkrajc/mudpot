package org.mudpot.game.text.cmd

import org.mudpot.text.cmd.Command
import org.mudpot.text.{Input, Output}


case object LookCommand extends Command {

  override def execute(input: Input, arguments: Map[String, String]): Output = {
    Output("Looking at " + arguments.mkString(","))
  }

  override def id: String = "look"
}
