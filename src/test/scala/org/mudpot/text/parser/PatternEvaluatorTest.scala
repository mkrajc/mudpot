package org.mudpot.text.parser


import org.mudpot.text.Input
import org.mudpot.text.pattern.PatternEvaluator
import org.mudpot.text.pattern.exp.{ExpressionPattern, Placeholder, Word}
import org.scalatest.{FlatSpec, Matchers}

class PatternEvaluatorTest extends FlatSpec with Matchers {

  behavior of "PatternEvaluatorTest"

  it should "find matching pattern" in {
    val lookPat = ExpressionPattern(List(Word("look"), Placeholder("object")))
    val eqPat = ExpressionPattern(List(Word("eq"), Placeholder("object")))

    val pe = new PatternEvaluator(List(lookPat, eqPat))
    val input = Input("look at sun")
    val result = pe.evaluate(input)
    result should be('defined)
    result.get.pattern should be(lookPat)
  }

  it should "find best matching pattern" in {
    val lookPat = ExpressionPattern(List(Word("look"), Placeholder("object")))
    val lookUnderPat = ExpressionPattern(List(Word("look"), Word("under"), Placeholder("object")))
    val pe = new PatternEvaluator(List(lookUnderPat, lookPat))
    val input1 = Input("look under table")
    val result1 = pe.evaluate(input1)
    result1 should be('defined)
    result1.get.pattern should be(lookUnderPat)

    val input2 = Input("look table")
    val result2 = pe.evaluate(input2)
    result2 should be('defined)
    result2.get.pattern should be(lookPat)
  }

  it should "find best matching pattern with different order" in {
    val lookPat = ExpressionPattern(List(Word("look"), Placeholder("object")))
    val lookUnderPat = ExpressionPattern(List(Word("look"), Word("under"), Placeholder("object")))
    val pe = new PatternEvaluator(List(lookPat, lookUnderPat))

    val input1 = Input("look under table")
    val result1 = pe.evaluate(input1)
    result1 should be('defined)
    result1.get.pattern should be(lookUnderPat)

    val input2 = Input("look table")
    val result2 = pe.evaluate(input2)
    result2 should be('defined)
    result2.get.pattern should be(lookPat)
  }

}
