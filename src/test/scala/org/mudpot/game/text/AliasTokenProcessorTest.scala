package org.mudpot.game.text

import org.mudpot.conf.DefaultFilenames
import org.mudpot.text.Input
import org.scalatest.{FlatSpec, Matchers}

class AliasTokenProcessorTest extends FlatSpec with Matchers {

  behavior of "AliasTokenProcessorTest"

  it should "return keywords for tokens representing alias" in {
    val aliasMap = Map("alias" -> "key")
    val processor = new AliasTokenProcessor(aliasMap)
    processor(Input("key alias other")).tokens should be(Input("key key other").tokens)
  }

  behavior of "FileAliasTokenProcessorTest"

  it should "load aliases from file and return keywords for tokens representing alias" in {
    import org.mudpot.conf.Paths.Implicits.development
    implicit val test = new DefaultFilenames {
      override def aliasFile: String = "test-alias.txt"
    }
    val processor = new FileAliasTokenProcessor
    processor(Input("key alias other")).tokens should be(Input("key key other").tokens)
  }

}
