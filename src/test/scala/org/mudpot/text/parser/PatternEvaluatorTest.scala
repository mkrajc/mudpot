package org.mudpot.text.parser


import org.scalatest.{Matchers, FlatSpec}

class PatternEvaluatorTest extends FlatSpec with Matchers {

  behavior of "PatternEvaluatorTest"

  it should "find matching pattern" in {
    val lookPat = ExpressionPattern(List(Word("look"), Placeholder))
    val eqPat = ExpressionPattern(List(Word("eq"), Placeholder))

    val pe = new PatternEvaluator(List(lookPat, eqPat))
    val result = pe.evaluate(List("look", "at", "sun"))
    result should be('defined)
    result.get._1 should be(lookPat)
  }

  it should "find best matching pattern" in {
    val lookPat = ExpressionPattern(List(Word("look"), Placeholder))
    val lookUnderPat = ExpressionPattern(List(Word("look"), Word("under"), Placeholder))
    val pe = new PatternEvaluator(List(lookUnderPat, lookPat))

    val result1 = pe.evaluate(List("look", "under", "table"))
    result1 should be('defined)
    result1.get._1 should be(lookUnderPat)

    val result2 = pe.evaluate(List("look", "table"))
    result2 should be('defined)
    result2.get._1 should be(lookPat)
  }

  it should "find best matching pattern with different order" in {
    val lookPat = ExpressionPattern(List(Word("look"), Placeholder))
    val lookUnderPat = ExpressionPattern(List(Word("look"), Word("under"), Placeholder))
    val pe = new PatternEvaluator(List(lookPat, lookUnderPat))

    val result1 = pe.evaluate(List("look", "under", "table"))
    result1 should be('defined)
    result1.get._1 should be(lookUnderPat)

    val result2 = pe.evaluate(List("look", "table"))
    result2 should be('defined)
    result2.get._1 should be(lookPat)
  }

}
