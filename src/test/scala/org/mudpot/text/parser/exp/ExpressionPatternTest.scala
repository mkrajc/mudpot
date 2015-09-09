package org.mudpot.text.parser.exp

import org.mudpot.text.Input
import org.mudpot.text.pattern.exp.{ExpressionPattern, Placeholder, Word}
import org.mudpot.text.pattern.{Matcher, NoMatch, OkMatch, Pattern}
import org.scalatest.{FlatSpec, Matchers}

class ExpressionPatternTest extends FlatSpec with Matchers {
  behavior of "ExpressionPattern"

  it should "return tokens with matching length and starting word" in {
    val pattern = new ExpressionPattern(List(Word("look"), Placeholder("object")))
    val input = Input("look sun")
    val matcher: Matcher = pattern.parse(input)
    matcher shouldBe an[OkMatch]
    matcher should be a 'matches
    matcher.parsed.get.value("object") should be("sun")

  }

  it should "return tokens with greater length and starting word" in {
    val pattern = new ExpressionPattern(List(Word("look"), Placeholder("object")))
    val input = Input("look sun moon")
    val matcher: Matcher = pattern.parse(input)
    matcher.parsed.get.values.toList should be(List("sun", "moon"))
  }

  it should "not return tokens with smaller length and starting word" in {
    val pattern = new ExpressionPattern(List(Word("look"), Placeholder("object")))
    val input = Input("look")
    val matcher: Matcher = pattern.parse(input)
    matcher shouldBe an[NoMatch.type]
  }

  it should "order according to expression" in {
    val lookUnder = new ExpressionPattern(List(Word("look"), Word("under"), Placeholder("object")))
    val look = new ExpressionPattern(List(Word("look"), Placeholder("object")))
    val take = new ExpressionPattern(List(Word("take"), Placeholder("object")))
    val give = new ExpressionPattern(List(Word("give"), Placeholder("object"), Word("to"), Placeholder("object2")))
    val nothing = new ExpressionPattern(List(Placeholder("object")))

    val pats: List[Pattern] = List(look, take, give, nothing, lookUnder)
    pats.sorted should be(List(give, lookUnder, look, take, nothing))
  }

  it should "create the pattern from text" in {
    val p = ExpressionPattern.from("something $big")
    p.expressions should be(List(Word("something"), Placeholder("big")))
  }

}
