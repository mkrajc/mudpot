package org.mudpot.game.text

import org.mudpot.conf.{Filenames, Paths}
import org.mudpot.io.{File, Loader}
import org.mudpot.text.{InputProcessor, Input}

class StopWordTokenProcessor(val stopWords: List[String]) extends InputProcessor {

  override def apply(input: Input): Input = input.copy(tokens = input.tokens.filterNot(stopWords.contains))

}

class FileStopWordTokenProcessor()(implicit val paths: Paths) extends InputProcessor {

  private val stopWords = loadStopWords()
  private val processor = new StopWordTokenProcessor(stopWords)

  private def loadStopWords(): List[String] = {
    val stopWordsFile: File = paths.parserDir.createFile(Filenames.stopWordsFile)
    println("Loading stop words: " + stopWordsFile)
    val stops = Loader.loadSource(stopWordsFile).getLines().toList
    println("Stop words: " + stops.mkString(","))
    stops
  }

  override def apply(input: Input): Input = processor(input)

}
