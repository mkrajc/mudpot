package org.mudpot.text.parser


trait Pattern extends Ordered[Pattern]{
  /**
   * Gives back the argument tokens, from tokens if it matches given pattern
   * @param tokens to parse
   * @return argument tokens or Nil if tokens not matching pattern
   */
  def parse(tokens: List[String]): List[String]
}
