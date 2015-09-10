package org.mudpot.text

trait Engine {
  def handle(req: String): String
}

case class SimpleEngine(parser: Parser) extends Engine {
  override def handle(req: String): String = parser.parse(Input(req)).text
}


