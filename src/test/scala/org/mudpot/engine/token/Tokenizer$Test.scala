package org.mudpot.engine.token

import org.scalatest.{FlatSpec, Matchers}

class Tokenizer$Test extends FlatSpec with Matchers{

  "Tokenizer" should "handle standard sentence" in {
    val tokens = Tokenizer.tokenize("what would Jesus do")
    tokens should be(List("what","would","Jesus","do"))
  }

  it should "handle multiple whitespace sentence" in {
    val tokens = Tokenizer.tokenize("  what   would \tJesus\ndo")
    tokens should be(List("what","would","Jesus","do"))
  }

  it should "handle empty sentence" in {
    val tokens = Tokenizer.tokenize("")
    tokens should be(Nil)
  }

  it should "handle empty long sentence" in {
    val tokens = Tokenizer.tokenize(" \t")
    tokens should be(Nil)
  }
}