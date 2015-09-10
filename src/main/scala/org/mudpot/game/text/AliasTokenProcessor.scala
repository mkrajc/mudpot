package org.mudpot.game.text

import java.util.Properties

import org.mudpot.conf.{Filenames, Paths}
import org.mudpot.io.{File, Loader}
import org.mudpot.text.{Input, InputProcessor}


class AliasTokenProcessor(val aliasMap: Map[String, String]) extends InputProcessor {

  override def apply(input: Input): Input = {
    val tokens = input.tokens map { t => aliasMap.getOrElse(t,t) }
    input.copy(tokens = tokens)
  }

}

class FileAliasTokenProcessor()(implicit val paths: Paths, implicit val filenames: Filenames) extends InputProcessor {

  private val aliasMap = loadAliasMap()
  private val processor = new AliasTokenProcessor(aliasMap)

  private def loadAliasMap(): Map[String, String] = {
    val aliasFile: File = paths.parserDir.createFile(filenames.aliasFile)
    println("Loading alias map: " + aliasFile)
    val prop = Loader.loadProperties(aliasFile)
    val map = scala.collection.JavaConversions.propertiesAsScalaMap(prop).toMap
    println(s"Aliases (${map.size}): ${map.mkString("[",",","]")}")
    map
  }

  override def apply(input: Input): Input = processor(input)

}
