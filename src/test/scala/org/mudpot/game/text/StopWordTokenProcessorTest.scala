package org.mudpot.game.text

import org.mudpot.conf.DefaultFilenames
import org.mudpot.text.Input
import org.scalatest.{FlatSpec, Matchers}

class StopWordTokenProcessorTest extends FlatSpec with Matchers {

  behavior of "StopWordTokenProcessor"

  it should "filter all define stop words" in {
    val processor = new StopWordTokenProcessor(List("at", "the"))
    processor(Input("look at the sun")).tokens should be(Input("look sun").tokens)
  }

  behavior of "FileStopWordTokenProcessor"

  it should "filter words from file" in {
    import org.mudpot.conf.Paths.Implicits.development
    implicit val test = new DefaultFilenames {
      override def stopWordsFile: String = "test-stop-words.txt"
    }

    val processor = new FileStopWordTokenProcessor
    processor(Input("look at the sun")).tokens should be(List("look", "sun"))
  }

}
