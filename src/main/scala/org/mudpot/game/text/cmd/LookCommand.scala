package org.mudpot.game.text.cmd

import org.mudpot.game.cmd.Look
import org.mudpot.obj.{PropertiesFilePropertyObject, PropertyObject}
import org.mudpot.path.Path
import org.mudpot.text.cmd.Command
import org.mudpot.text.{Input, Output}


case object LookCommand extends Command {


  val cmd = new Look


  override def execute(input: Input, arguments: Map[String, String]): Output = {
    val obj: PropertyObject = new PropertiesFilePropertyObject() {
      override def location: Path = Path("/test/" + arguments.get("object").getOrElse("none"))
    }
    cmd.lookAt(obj)

  }

  override def id: String = "look"
}
