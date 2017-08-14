package org.mudpot.text.engine

import org.mudpot.game.text.cmd.UnknownCommand
import org.mudpot.game.text.{FileAliasTokenProcessor, FileStopWordTokenProcessor}
import org.mudpot.log.HasLogger
import org.mudpot.text.cmd.SimpleCommandResolver
import org.mudpot.text.{SimpleParser, Parser}
import org.mudpot.text.pattern.{PatternEvaluator, FilePatternLoader}
import org.mudpot.text.pattern.exp.ExpressionPattern

/**
 * Returns same engine for all users
 */
object GlobalEngineFactory extends EngineFactory with HasLogger {
  override def create(user: String): Engine = {
    import org.mudpot.conf.Paths.Implicits.development
    import org.mudpot.conf.Filenames.Implicits.defaults

    logger.info(s"Creating engine for user '$user'.")
    val stop = new FileStopWordTokenProcessor
    val alias = new FileAliasTokenProcessor
    val patterns = new FilePatternLoader(ExpressionPattern.from).load()
    val patternEvaluator = new PatternEvaluator(patterns)
    val parser: Parser = new SimpleParser(patternEvaluator, SimpleCommandResolver, UnknownCommand, List(stop, alias), Nil)
    new SimpleEngine(parser)
  }
}
