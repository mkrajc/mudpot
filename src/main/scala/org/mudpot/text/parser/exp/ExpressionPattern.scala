package org.mudpot.text.parser.exp

import org.mudpot.text.Input
import org.mudpot.text.parser._

case class ExpressionPattern(expressions: List[Expression]) extends Pattern {
  override def parse(input: Input): Matcher = {
    if (expressions.length > input.tokens.length) NoMatch
    else {
      val padded = expressions.padTo(math.max(expressions.length, input.tokens.length), Placeholder(""))
      val (words, placeholders) = padded.zip(input.tokens).partition {
        case (word: Word, token: String) => true
        case _ => false
      }

      val wordsOk = words.forall(pair => pair._1.asInstanceOf[Word].word.equals(pair._2))

      if (wordsOk) {
        val argsMap = placeholders.map { p => (p._1.asInstanceOf[Placeholder].arg, p._2) }.toMap
        OkMatch(new ParsedInputMap(argsMap))
      } else NoMatch
    }
  }

  override def compare(that: Pattern): Int = {
    val expPattern = that.asInstanceOf[ExpressionPattern]
    val firstWord = (expressions.head, expPattern.expressions.head) match {
      case (a: Word, b: Word) => a.word.compare(b.word)
      case _ => 0
    }

    if (firstWord == 0) expPattern.expressions.size.compareTo(expressions.size) else firstWord
  }

  override val arguments: List[String] = expressions.collect { case c: Placeholder => c.arg }

}
