package org.mudpot.text.token

class StopWordTokenProcessor(val stopWords: List[String]) extends TokenProcessor {

  override def apply(tokens: List[String]): List[String] = tokens.filterNot(stopWords.contains)

}
