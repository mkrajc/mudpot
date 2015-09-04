package org.mudpot.text.token

import org.scalatest.{Matchers, FlatSpec}

class StopWordTokenProcessorTest extends FlatSpec with Matchers {

  behavior of "StopWordTokenProcessor"

  it should "filter all define stop words" in {
    val processor = new StopWordTokenProcessor(List("at", "the"))
    processor(List("look", "at", "the", "sun")) should be(List("look", "sun"))
  }

}
