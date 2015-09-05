package org.mudpot.text

import org.mudpot.text.token.Tokenizer

case class Input(original: String, tokens: List[String]) {
  lazy val normalized = tokens.mkString(" ")
}

object Input {
  def apply(text: String): Input = Input(text, Tokenizer.tokenize(text))
}
