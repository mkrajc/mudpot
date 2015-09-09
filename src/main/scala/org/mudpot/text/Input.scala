package org.mudpot.text

case class Input(original: String, tokens: List[String]) {
  lazy val normalized = tokens.mkString(" ")
}

object Input {
  def apply(text: String): Input = Input(text, Tokenizer.tokenize(text))
}

trait InputProcessor extends Function[Input, Input]
