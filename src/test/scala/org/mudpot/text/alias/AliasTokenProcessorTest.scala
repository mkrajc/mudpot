package org.mudpot.text.alias

import org.mudpot.game.text.AliasTokenProcessor
import org.mudpot.game.text.token.MapAliasMap
import org.scalatest.{Matchers, FlatSpec}

class AliasTokenProcessorTest extends FlatSpec with Matchers {

  behavior of "AliasTokenProcessorTest"

  it should "return keywords for tokens representing alias" in {
    val aliasMap = new MapAliasMap(Map("key" -> List("alias")))
    //val processor = new AliasTokenProcessor(aliasMap)
    //processor(List("key", "alias", "other")) should be(List("key", "key", "other"))
  }

}
