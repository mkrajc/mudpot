package org.mudpot.text.alias

import org.scalatest.{FlatSpec, Matchers}


class MapAliasMapTest extends FlatSpec with Matchers {

  "MapAliasMap" should "return aliases from map" in {
    val map = Map("a" -> List("1", "2"), "b" -> List("3", "4"))
    val aliasMap = new MapAliasMap(map)
    aliasMap.getAliases("b").get should be(List("3","4"))
  }

  "MapAliasMap" should "return keyword from alias" in {
    val map = Map("a" -> List("1", "2"), "b" -> List("3", "4"))
    val aliasMap = new MapAliasMap(map)
    aliasMap.getKeyword("4").get should be("b")
    aliasMap.getKeyword("1").get should be("a")
  }

  "MapAliasMap" should "return keyword from not existing alias" in {
    val map = Map("a" -> List("1", "2"), "b" -> List("3", "4"))
    val aliasMap = new MapAliasMap(map)
    aliasMap.getKeyword("5") should be(None)
  }

  "MapAliasMap" should "not create instance if aliases overlap in map" in {
    intercept[IllegalArgumentException] {
      val map = Map("a" -> List("1", "2"), "b" -> List("3", "1"))
      new MapAliasMap(map)
    }

  }

}
