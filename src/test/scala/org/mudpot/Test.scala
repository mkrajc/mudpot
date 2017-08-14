package org.mudpot

import org.scalacheck.Gen

import scala.util.Random


class Test() {
  def f[K, V, T](map: T, k: K)(implicit ev: T => Map[K, V]): V = map(k)

  def f2[K, V, T <% Map[K, V]](map: T, k: K): V = map(k)
}

class A {
  override def toString: String = "hello from a"
}


object WTF {

  def main(args: Array[String]) {
    val g = Gen.choose(Double.MinValue + 1000.0, Double.MaxValue - 1000.0)
    println(g.sample)
    println(g.sample)
   // println(internalNextDouble(400,500))
  }



}

