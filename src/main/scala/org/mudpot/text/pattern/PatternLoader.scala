package org.mudpot.text.pattern

import org.mudpot.conf.{Filenames, Paths}
import org.mudpot.io.Loader

trait PatternLoader {
  def load(): List[Pattern]
}

class FilePatternLoader(p: String => Pattern)(implicit val paths: Paths, implicit val filenames: Filenames) extends PatternLoader {
  override def load(): List[Pattern] = {
    val patternFile = paths.parserDir.createFile(filenames.patternFile)
    println("Loading patterns: " + patternFile)
    val source = Loader.loadSource(patternFile)
    source.getLines().map(p).toList
  }
}
