package org.mudpot.engine.cmd

import org.mudpot.engine.Command
import org.mudpot.game.text.{FileAliasTokenProcessor, FileStopWordTokenProcessor}
import org.mudpot.text.{InputProcessor, Input}
import org.mudpot.text.pattern.exp.ExpressionPattern
import org.mudpot.text.pattern.{FilePatternLoader, PatternEvaluator}

trait CommandResolver {
  def resolve(input: Input): Command
}

class CommandResolverImpl(inputProcessors: List[InputProcessor],
                          val patternEvaluator: PatternEvaluator) extends CommandResolver {
  override def resolve(input: Input): Command = {
    val processedInput = inputProcessors.foldLeft(input)(
      (nextInput, processor) => processor(nextInput))
    val parsedInput = patternEvaluator.evaluate(processedInput)
    parsedInput.map(pi => {
      println(pi)
      pi.pattern.arguments.foreach(v => println(s"arg=$v val=${pi.value(v)}"))
    })
    null
  }
}

object CommandResolverImpl {
  def main(args: Array[String]) {
    import org.mudpot.conf.Paths.Implicits.development

    val stop = new FileStopWordTokenProcessor
    val alias = new FileAliasTokenProcessor
    val patterns = new FilePatternLoader(ExpressionPattern.from).load()
    val patternEvaluator = new PatternEvaluator(patterns)
    val resolver = new CommandResolverImpl(List(stop,alias), patternEvaluator)
    resolver.resolve(Input("time"))
    resolver.resolve(Input("give the ring to the boy"))
  }
}
