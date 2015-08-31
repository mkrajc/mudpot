package org.mudpot.engine

trait Engine {
  def handle(req: String): String
}

trait Commands {
  def addCommand(cmd: Command)

  def removeCommand(cmd: Command)
}

trait Command {

  def name: String

  def aliases: Array[String]

  def pattern: String

  def execute(args: List[String]): String

}
