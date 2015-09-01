package org.mudpot.engine.cmd

import org.mudpot.engine.Command

class LookCommand extends Command{

  override def pattern: String = "${cmd} ${object}"

  override def execute(args: List[String]): String = ???

  override def name: String = ???
}
