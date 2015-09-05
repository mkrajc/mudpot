package org.mudpot.text.parser

import org.mudpot.text.Input
import org.mudpot.utils.CollectionUtils


class PatternEvaluator(patterns: List[Pattern]) {

  private val sortPatterns: List[Pattern] = patterns.sorted

  def evaluate(input: Input): Option[ParsedInput] = {
    val matcher = CollectionUtils.findAndMap[Pattern, Matcher](sortPatterns, p => {
      val matcher = p.parse(input)
      (matcher.matches, matcher)
    })

    if (matcher.isDefined) matcher.get.parsed else None
  }


}
