package org.mudpot.text.pattern.exp

trait Expression

case class Word(word: String) extends Expression {
  override def toString: String = word
}

case class Placeholder(arg: String) extends Expression {
  override def toString: String = "$" + arg
}
