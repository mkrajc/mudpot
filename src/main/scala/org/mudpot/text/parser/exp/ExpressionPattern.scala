package org.mudpot.text.parser.exp

import org.mudpot.text.Input
import org.mudpot.text.parser._
import org.mudpot.text.token.Tokenizer

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
        OkMatch(new ParsedInputMap(this, argsMap))
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

  override def toString: String = expressions.mkString(" ")
}

object ExpressionPattern {
  def from(text: String): ExpressionPattern = {
    val expressions: List[Expression] = Tokenizer.tokenize(text).map(p => {
      if (p.startsWith("$")) Placeholder(p.substring(1))
      else Word(p)
    })
    ExpressionPattern(expressions)
  }
}
