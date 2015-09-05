package org.mudpot.text.parser

import org.mudpot.text.Input


trait Pattern extends Ordered[Pattern] {

  def parse(input: Input): Matcher

  def arguments: List[String]

}
