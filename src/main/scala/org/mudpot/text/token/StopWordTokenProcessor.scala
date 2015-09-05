package org.mudpot.text.token

import org.mudpot.conf.{Filenames, Paths}
import org.mudpot.io.{File, Loader}

class StopWordTokenProcessor(val stopWords: List[String]) extends TokenProcessor {

  override def apply(tokens: List[String]): List[String] = tokens.filterNot(stopWords.contains)

}

class FileStopWordTokenProcessor()(implicit val paths: Paths) extends TokenProcessor {

  private val stopWords = loadStopWords()
  private val processor = new StopWordTokenProcessor(stopWords)

  private def loadStopWords(): List[String] = {
    val stopWordsFile: File = paths.parserDir.createFile(Filenames.stopWordsFile)
    println("Loading stop words: " + stopWordsFile)
    val stops = Loader.loadSource(stopWordsFile).getLines().toList
    println("Stop words: " + stops.mkString(","))
    stops
  }

  override def apply(tokens: List[String]): List[String] = processor(tokens)

}
