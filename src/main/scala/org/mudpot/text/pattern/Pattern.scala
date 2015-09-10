package org.mudpot.text.pattern

import org.mudpot.text.Input


trait Pattern extends Ordered[Pattern] {

  def parse(input: Input): Matcher

  def arguments: List[String]

  def commandId: String

}
