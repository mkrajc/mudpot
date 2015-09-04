package org.mudpot.text.parser

trait Expression

case class Word(word: String) extends Expression

case object Placeholder extends Expression

case class ExpressionPattern(expressions: List[Expression]) extends Pattern {

  def parse(tokens: List[String]): List[String] = {
    if (expressions.length > tokens.length) Nil
    else {
      val padded = expressions.padTo(math.max(expressions.length, tokens.length), Placeholder)
      val (words, placeholders) = padded.zip(tokens).partition {
        case (word: Word, token: String) => true
        case _ => false
      }

      val wordsOk = words.forall(pair => pair._1.asInstanceOf[Word].word.equals(pair._2))

      if (wordsOk) placeholders.map(_._2) else Nil
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
}




