package org.mudpot.game.text

import java.util
import java.util.Map.Entry
import java.util.Properties

import org.mudpot.conf.{Filenames, Paths}
import org.mudpot.io.{File, Loader}
import org.mudpot.text.{InputProcessor, Input}

import scala.collection.mutable
import scala.io.Source


class AliasTokenProcessor(val aliasMap: Map[String, String]) extends InputProcessor {
  val log: PartialFunction[String, String] = {
    case x => println(s"aliasing $x"); x
  }

  val ident: PartialFunction[String, String] = {
    case x => x
  }

  override def apply(input: Input): Input = {
    val tokens = input.tokens map { t => aliasMap.andThen(log).orElse(ident)(t) }
    input.copy(tokens = tokens)
  }

}

class FileAliasTokenProcessor()(implicit val paths: Paths) extends InputProcessor {

  private val aliasMap = loadAliasMap()
  private val processor = new AliasTokenProcessor(aliasMap)

  private def loadAliasMap(): Map[String, String] = {
    val aliasFile: File = paths.parserDir.createFile(Filenames.aliasFile)
    println("Loading alias map: " + aliasFile)
    val prop = new Properties()
    prop.load(Loader.loadInputStream(aliasFile))
    val map = scala.collection.JavaConversions.propertiesAsScalaMap(prop).toMap
    println(map.mkString("\n"))
    map
  }

  override def apply(input: Input): Input = processor(input)

}
