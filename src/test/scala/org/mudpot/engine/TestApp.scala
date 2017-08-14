package org.mudpot.engine

import org.mudpot.game.text.cmd.UnknownCommand
import org.mudpot.game.text.{FileAliasTokenProcessor, FileStopWordTokenProcessor}
import org.mudpot.text.cmd.SimpleCommandResolver
import org.mudpot.text.engine.{SimpleEngine, Engine}
import org.mudpot.text.{SimpleParser, Parser}
import org.mudpot.text.pattern.{PatternEvaluator, FilePatternLoader}
import org.mudpot.text.pattern.exp.ExpressionPattern

import scala.io.StdIn
import org.mudpot.conf.Paths.Implicits.development
import org.mudpot.conf.Filenames.Implicits.defaults

object TestApp {

  val stop = new FileStopWordTokenProcessor
  val alias = new FileAliasTokenProcessor
  val patterns = new FilePatternLoader(ExpressionPattern.from).load()
  val patternEvaluator = new PatternEvaluator(patterns)
  val parser: Parser = new SimpleParser(patternEvaluator, SimpleCommandResolver, UnknownCommand, List(stop, alias), Nil)
  val engine: Engine = new SimpleEngine(parser)

  def main(args: Array[String]) {
    var ok = true
    while (ok) {
      val ln = StdIn.readLine()
      if ("q" == ln) {
        println("Exiting")
        ok = false
      } else {
        println(engine.handle(ln))
      }
    }
  }
}
