package org.mudpot.utils

object CollectionUtils {

  def findAndMap[T, B](list: Iterable[T], p: T => (Boolean, B)): Option[B] = {
    var these = list
    while (!these.isEmpty) {
      val result = p(these.head)
      if (result._1) return Some(result._2)
      these = these.tail
    }
    None
  }
}
