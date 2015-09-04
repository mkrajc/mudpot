package org.mudpot.engine.cmd

import org.mudpot.engine.Command


trait CommandBuilder[T <: Command] {
  def args(args: List[String])

  def toCommand: T
}
