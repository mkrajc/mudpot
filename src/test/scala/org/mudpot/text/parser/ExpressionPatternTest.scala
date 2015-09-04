package org.mudpot.text.parser

import org.scalatest.{Matchers, FlatSpec}

class ExpressionPatternTest extends FlatSpec with Matchers {
  behavior of "ExpressionPattern"

  it should "return tokens with matching length and starting word" in {
    val pattern = new ExpressionPattern(List(Word("look"), Placeholder))
    pattern.parse(List("look", "sun")) should be(List("sun"))
  }

  it should "return tokens with greater length and starting word" in {
    val pattern = new ExpressionPattern(List(Word("look"), Placeholder))
    pattern.parse(List("look", "sun", "moon")) should be(List("sun", "moon"))
  }

  it should "not return tokens with smaller length and starting word" in {
    val pattern = new ExpressionPattern(List(Word("look"), Placeholder))
    pattern.parse(List("look")) should be(Nil)
  }

  it should "order according to expression" in {
    val lookUnder = new ExpressionPattern(List(Word("look"), Word("under"), Placeholder))
    val look = new ExpressionPattern(List(Word("look"), Placeholder))
    val take = new ExpressionPattern(List(Word("take"), Placeholder))
    val give = new ExpressionPattern(List(Word("give"), Placeholder, Word("to"), Placeholder))
    val nothing = new ExpressionPattern(List(Placeholder))

    val pats: List[Pattern] = List(look, take, give, nothing, lookUnder)
    pats.sorted should be(List(give, lookUnder, look, take, nothing))
  }
}
