package org.mudpot.game.text.token

import scala.collection.mutable


trait AliasMap {
  def getAliases(keyword: String): Option[List[String]]

  /**
   * Return keyword word
   * @param word
   * @return exact one keyword or word itself if alias not exist
   */
  def getKeyword(word: String): Option[String]

}

class MapAliasMap(map: Map[String, List[String]]) extends AliasMap {

  private val reverseMap = buildMap(map flatten { case (key, v) => v.map(alias => (alias, key)) })

  override def getAliases(keyword: String): Option[List[String]] = map.get(keyword)

  override def getKeyword(word: String): Option[String] = {
    //TODO optimize searching without reverse map
    reverseMap.get(word)
  }

  private def buildMap(in: TraversableOnce[(String, String)]): Map[String, String] = {
    val map = new mutable.HashMap[String, String]
    val it = in.toIterator

    while (it.hasNext) {
      val next = it.next()
      val old = map.put(next._1, next._2)
      if (old.isDefined) {
        throw new IllegalArgumentException(s"alias '${next._1}' is defined for keywords '${old.get}' and '${next._2}'")
      }
    }
    map.toMap
  }

}
