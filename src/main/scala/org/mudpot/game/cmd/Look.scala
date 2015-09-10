package org.mudpot.game.cmd

import org.mudpot.obj.PropertyObject
import org.mudpot.text.Output

class Look {
  def lookAt(obj: PropertyObject): Output = {
    val lookDesc: Option[String] = obj.getString("cmd.look")
    Output(lookDesc.getOrElse("doesn't know how to look at object " + obj))
  }
}