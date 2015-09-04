package org.mudpot.text.parser


class PatternEvaluator(patterns: List[Pattern]) {

  private val sortPatterns: List[Pattern] = patterns.sorted

  def evaluate(tokens: List[String]): Option[(Pattern, List[String])] = {
    var args: List[String] = Nil
    val p = sortPatterns.find(p => {
      args = p.parse(tokens)
      args.nonEmpty
    })
    p.map(pat => (pat, args))
  }
}
