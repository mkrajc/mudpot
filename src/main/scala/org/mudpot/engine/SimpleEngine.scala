package org.mudpot.engine

import org.mudpot.engine.cmd.UnknownCommand
import org.mudpot.text.token.Tokenizer


class SimpleEngine extends Engine with Commands {
  var commands: List[Command] = Nil

  override def handle(req: String): String = {
    val tokens = Tokenizer.tokenize(req)
    val command = if (tokens.isEmpty) {
      UnknownCommand
    } else {
      commands.find(_.name == tokens.head).getOrElse(UnknownCommand)
    }

    command.execute(tokens)
  }


  override def removeCommand(cmd: Command): Unit = ???

  override def addCommand(cmd: Command): Unit = commands = cmd :: commands
}
