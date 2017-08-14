package org.mudpot.text


case class Output(text: String)


object Output {

  def process(output: Output, processors: List[OutputProcessor]): Output = {
    processors.foldLeft(output)((i, proc) => proc(i))
  }
}

trait OutputProcessor extends Function[Output, Output]

class Prompt(prompt: String) extends OutputProcessor {
  override def apply(output: Output): Output = output.copy(output.text + "\n" +prompt)
}