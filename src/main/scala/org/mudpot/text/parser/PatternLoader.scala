package org.mudpot.text.parser


import org.mudpot.conf.{Filenames, Paths}
import org.mudpot.io.Loader

trait PatternLoader {
  def load(): List[Pattern]
}

class FilePatternLoader(p: String => Pattern)(implicit val paths: Paths) extends PatternLoader {
  override def load(): List[Pattern] = {
    val patternFile = paths.parserDir.createFile(Filenames.patternFile)
    println("Loading patterns: " + patternFile)
    val source = Loader.loadSource(patternFile)
    source.getLines().map(p).toList
  }
}
