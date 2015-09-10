package org.mudpot.text

case class Input(original: String, tokens: List[String]) {
  lazy val normalized = tokens.mkString(" ")
}

object Input {
  def apply(text: String): Input = Input(text, Tokenizer.tokenize(text))

  def process(input: Input, processors: List[InputProcessor]): Input = {
    processors.foldLeft(input)((i, proc) => proc(i))
  }
}

trait InputProcessor extends Function[Input, Input]
