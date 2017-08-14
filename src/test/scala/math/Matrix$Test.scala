package math

import org.scalatest.{FlatSpec, Matchers}
import matrix._
import matrix.Matrix._

import scala.collection.mutable.ListBuffer

class Matrix$Test extends FlatSpec with Matchers {

  behavior of "Matrix"

  it should "map over m" in {
    val m1 = fillSquare(3, 0)
    println(m1.map(_.toString + "!"))
  }

  it should "add two matrices" in {
    val m1 = fillSquare(3, 1)
    val m2 = fillSquare(3, 2)
    val m3 = m1 + m2
    println(m3)
  }

  it should "multiply matrix by scalar " in {
    val m = fillSquare(3, 1)
    val r = m * 4
    println(r)
  }

  it should "create matrix by definition " in {
    val a = create(2, 3)(2, 1, 0, 1, 3, 5)
    println(a)
  }

  it should "multiply two matrices " in {
    val a = Matrix.create(2, 3)(1, 0, 1, 2, 0, 2)
    val b = Matrix.create(3, 2)(2, 0, 1, 1, 0, 1)
    val c = a * b
    //a.rowIterator.foreach(i => println(i.toList))
    //a.columnIterator.foreach(i => println(i.toList))
    println(c.mkString)

  }

  it should "swap flip " in {
    val a = Matrix.create(2, 3)(1, 2, 3, 4, 5, 6)
    println(a.mkString)
    println(a.flip.mkString)
  }

  it should "create diagonal " in {
    val a = diagonal(3, 1, 0)
  }

  it should "behave performance " in {

  }


  behavior of "Vector ops"

  it should "allow dot product " in {
    val a = Vector(1, 3, -5)
    val b = Vector(4, -2, -1)
    println(a dot b)
  }

}
