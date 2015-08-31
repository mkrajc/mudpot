package org.mudpot.engine


class SimpleEngine extends Engine with Commands {
  var commands: List[Command] = Nil

  override def handle(req: String): String = {
    commands.find(_.name == req).map(c => c.execute(List(req))).getOrElse("unknown")
  }


  override def removeCommand(cmd: Command): Unit = ???

  override def addCommand(cmd: Command): Unit = commands = cmd :: commands
}
