package org.mudpot.text.cmd

import org.mudpot.text.{Input, Output}

trait Command {
  def id: String

  def execute(input: Input, arguments: Map[String, String]): Output

}

