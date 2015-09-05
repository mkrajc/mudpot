package org.mudpot.text.parser

trait Matcher {
  def matches: Boolean

  def parsed: Option[ParsedInput]
}

case class OkMatch(parsedInput: ParsedInput) extends Matcher {
  override def matches: Boolean = true

  override def parsed: Option[ParsedInput] = Some(parsedInput)
}

case object NoMatch extends Matcher {
  override def matches: Boolean = false

  override def parsed: Option[ParsedInput] = None
}

trait ParsedInput {
  def values: Iterable[String]

  def value(arg: String): String

  def pattern: Pattern
}

case class ParsedInputMap(pattern: Pattern, argsMap: Map[String, String]) extends ParsedInput {
  override def value(arg: String): String = argsMap.getOrElse(arg, arg)

  override def values: Iterable[String] = argsMap.values
}
