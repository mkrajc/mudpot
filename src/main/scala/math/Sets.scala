package math


object Sets {

  trait MSet[A] {
    def contains(a: A): Boolean
  }

}

trait Category[->>[_, _]] extends {
  def unit[A]: A ->> A

  def compose[A, B, C](f: A ->> B, g: B ->> C): A ->> C
}

trait Monoid[T] {
  def unit: T

  def op(a: T, b: T): T
}

case object IntAddMonoid extends Monoid[Int] {
  override def unit: Int = 0

  override def op(a: Int, b: Int): Int = a + b
}

class MonoidCategory[T](monoid: Monoid[T]) extends Category[Function1]{
  override def unit[A]: (A) => A = _ => monoid.unit.asInstanceOf[A]

  override def compose[A, B, C](f: (A) => B, g: (B) => C): (A) => C = g compose f
}

object Test {
  def main(args: Array[String]) {
    val cat = new MonoidCategory(IntAddMonoid)

  }
}

