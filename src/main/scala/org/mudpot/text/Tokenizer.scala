package org.mudpot.text

import java.util.regex.Pattern

object Tokenizer {
  private val pattern = Pattern.compile("\\s+")

  def tokenize(text: String): List[String] = if (text.trim.isEmpty) Nil else pattern.split(text.trim).toList

  def trim(text: String): String = tokenize(text).mkString(" ")
}
