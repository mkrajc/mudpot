package org.mudpot.game.text.cmd

import org.mudpot.text.cmd.Command
import org.mudpot.text.{Input, Output}


case object UnknownCommand extends Command {
  override def execute(input: Input, arguments: Map[String, String]): Output =
    Output("unknown command " + input.original)

  override def id: String = "unknown"
}


