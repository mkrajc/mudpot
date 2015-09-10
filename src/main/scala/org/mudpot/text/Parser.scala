package org.mudpot.text

import org.mudpot.text.cmd.{Command, CommandResolver}
import org.mudpot.text.pattern.PatternEvaluator

trait Parser {
  def parse(input: Input): Output
}

class SimpleParser(patternEvaluator: PatternEvaluator, commandResolver: CommandResolver, default: Command, processors: List[InputProcessor]) extends Parser {
  override def parse(input: Input): Output = {
    val next: Input = Input.process(input, processors)

    val output: Option[Output] = for {
      in <- patternEvaluator.evaluate(next)
      cmd <- commandResolver.resolve(in.pattern.commandId)
    } yield cmd.execute(next, in.args)

    output.getOrElse(default.execute(next, Map()))

  }
}
