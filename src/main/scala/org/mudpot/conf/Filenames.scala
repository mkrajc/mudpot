package org.mudpot.conf

trait Filenames {
  def patternFile: String

  def stopWordsFile: String

  def aliasFile: String
}

class DefaultFilenames extends Filenames {
  override def patternFile = "patterns.txt"

  override def stopWordsFile = "stops.txt"

  override def aliasFile = "alias.properties"
}

object Filenames {

  object Implicits {
    implicit lazy val defaults: Filenames = new DefaultFilenames
  }

}
