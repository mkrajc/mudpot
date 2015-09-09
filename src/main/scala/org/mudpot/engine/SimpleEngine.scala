package org.mudpot.engine

import org.mudpot.text.{InputProcessor, Input}
import org.mudpot.text.pattern.PatternEvaluator


class SimpleEngine(tokenProcessors: List[InputProcessor], val patternEvaluator: PatternEvaluator) extends Engine with Commands {
  var commands: List[Command] = Nil

  override def handle(req: String): String = {
    val input = Input(req)
    // val processed = tokenProcessors.foldLeft(tokens)((nextTokens, processor) => processor(nextTokens))
    val patAndArgs = patternEvaluator.evaluate(input)
    patAndArgs.map(pair => println(pair.values.mkString))
    ""
  }


  override def removeCommand(cmd: Command): Unit = ???

  override def addCommand(cmd: Command): Unit = commands = cmd :: commands
}
