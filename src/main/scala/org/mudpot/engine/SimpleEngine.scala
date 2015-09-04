package org.mudpot.engine

import org.mudpot.engine.cmd.UnknownCommand
import org.mudpot.text.parser.PatternEvaluator
import org.mudpot.text.token.{TokenProcessor, Tokenizer}


class SimpleEngine(tokenProcessors: List[TokenProcessor], val patternEvaluator: PatternEvaluator) extends Engine with Commands {
  var commands: List[Command] = Nil

  override def handle(req: String): String = {
    val tokens = Tokenizer.tokenize(req)
    val processed = tokenProcessors.foldLeft(tokens)((nextTokens, processor) => processor(nextTokens))
    val patAndArgs = patternEvaluator.evaluate(processed)
    patAndArgs.map(pair => println(pair._2))
    ""
  }


  override def removeCommand(cmd: Command): Unit = ???

  override def addCommand(cmd: Command): Unit = commands = cmd :: commands
}
