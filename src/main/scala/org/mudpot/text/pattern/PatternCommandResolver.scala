package org.mudpot.text.pattern

import org.mudpot.text.Input

import scala.reflect.ClassTag

class PatternDispatcher(patternEvaluator: PatternEvaluator)   {


  def resolve(input: Input): Option[ParsedInput] = {
    val parsedInputOption: Option[ParsedInput] = patternEvaluator.evaluate(input)

    if (parsedInputOption.isDefined) {
      val parsedInput: ParsedInput = parsedInputOption.get
      val commandId = parsedInput.pattern.commandId

      println(s"command id found = $commandId")

      val declared = ClassTag(Class.forName("org.mudpot.game.text.LookCommand")).runtimeClass.getDeclaredConstructors
      val params = declared.head.getParameterTypes

      println(params.mkString(","))

      None

    } else {
      println(s"no pattern match input $input")
      None
    }

  }
}
