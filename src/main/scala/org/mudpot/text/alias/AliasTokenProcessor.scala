package org.mudpot.text.alias

import org.mudpot.text.token.TokenProcessor


class AliasTokenProcessor(val aliasMap: AliasMap) extends TokenProcessor {
  override def apply(tokens: List[String]): List[String] =
    tokens map { token => aliasMap.getKeyword(token).getOrElse(token) }

}
