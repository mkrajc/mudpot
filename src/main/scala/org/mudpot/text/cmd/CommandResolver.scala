package org.mudpot.text.cmd

import org.mudpot.game.text.cmd.LookCommand

trait CommandResolver {
  def resolve(commandId: String): Option[Command]
}

case object SimpleCommandResolver extends CommandResolver {
  override def resolve(commandId: String): Option[Command] = commandId match {
    case "look" => Some(LookCommand)
    case _ => None
  }
}