package org.mudpot.text.cmd

import org.mudpot.game.text.cmd.{LookCommand, UnknownCommand}
import org.mudpot.game.text.{FileAliasTokenProcessor, FileStopWordTokenProcessor}
import org.mudpot.text._
import org.mudpot.text.pattern.exp.ExpressionPattern
import org.mudpot.text.pattern.{FilePatternLoader, PatternEvaluator}

trait CommandResolver {
  def resolve(commandId: String): Option[Command]
}

case object SimpleCommandResolver extends CommandResolver {
  override def resolve(commandId: String): Option[Command] = commandId match {
    case "look" => Some(LookCommand)
    case _ => None
  }
}

object CommandResolverImpl {
  def main(args: Array[String]) {
    import org.mudpot.conf.Paths.Implicits.development
    import org.mudpot.conf.Filenames.Implicits.defaults

    val stop = new FileStopWordTokenProcessor
    val alias = new FileAliasTokenProcessor
    val patterns = new FilePatternLoader(ExpressionPattern.from).load()
    val patternEvaluator = new PatternEvaluator(patterns)
    val parser: Parser = new SimpleParser(patternEvaluator, SimpleCommandResolver, UnknownCommand, List(stop, alias))
    val engine: Engine = new SimpleEngine(parser)
    println(engine.handle("look at sun"))
    println(engine.handle("give the ring to boy"))
  }
}
