package org.mudpot.utils

object EnumUtils {

  class PrevNextEnum extends Enumeration {
    lazy val prevOf = {
      val list = values.toList
      val map = list.tail.zip(list).toMap
      v: Value => map.get(v)
    }
    lazy val nextOf = {
      val list = values.toList
      val map = list.zip(list.tail).toMap
      v: Value => map.get(v)
    }
  }

  class CyclicEnum extends Enumeration {
    lazy val prevOf = {
      val list = values.toList
      val map = list.tail.zip(list).toMap
      v: Value => map.getOrElse(v, list.last)
    }
    lazy val nextOf = {
      val list = values.toList
      val map = list.zip(list.tail).toMap
      v: Value => map.getOrElse(v, list.head)
    }
  }

}