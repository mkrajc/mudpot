package org.mudpot.text.token

import org.mudpot.game.text.StopWordTokenProcessor
import org.mudpot.text.Input
import org.scalatest.{Matchers, FlatSpec}

class StopWordTokenProcessorTest extends FlatSpec with Matchers {

  behavior of "StopWordTokenProcessor"

  it should "filter all define stop words" in {
    val processor = new StopWordTokenProcessor(List("at", "the"))
    processor(Input("look at the sun")) should be(Input("look sun"))
  }

}
