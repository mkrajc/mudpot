package org.mudpot.text.parser.exp

trait Expression

case class Word(word: String) extends Expression

case class Placeholder(arg: String) extends Expression
