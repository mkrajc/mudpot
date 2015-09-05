package org.mudpot.engine.cmd

import org.mudpot.engine.Command
import org.mudpot.text.Input
import org.mudpot.text.parser.exp.ExpressionPattern
import org.mudpot.text.parser.{FilePatternLoader, PatternEvaluator}
import org.mudpot.text.token.{StopWordTokenProcessor, TokenProcessor}

trait CommandResolver {
  def resolve(input: Input): Command
}

class CommandResolverImpl(tokenProcessors: List[TokenProcessor],
                          val patternEvaluator: PatternEvaluator) extends CommandResolver {
  override def resolve(input: Input): Command = {
    val processedInput = tokenProcessors.foldLeft(input)(
      (nextInput, processor) => nextInput.copy(tokens = processor(nextInput.tokens)))
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

    val stop = new StopWordTokenProcessor(List("the"))
    val patterns = new FilePatternLoader(ExpressionPattern.from).load()
    val patternEvaluator = new PatternEvaluator(patterns)
    val resolver = new CommandResolverImpl(List(stop), patternEvaluator)
    resolver.resolve(Input("date"))
    resolver.resolve(Input("give the ring to the boy"))
  }
}
