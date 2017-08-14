package math
import scala.collection.mutable.ListBuffer

object matrix {

  type Size = (Int, Int)

  sealed trait Matrix[A] {
    def size: Size
    def rows: Int = size._1
    def columns: Int = size._2
    /**
     * i in m, j in n
     */
    def get(i: Int, j: Int): A
    def map[B](f: A => B): Matrix[B]
    def iterator: Iterator[A]
    def rowIterator: Iterator[Iterator[A]]
    def columnIterator: Iterator[Iterator[A]] = flip.rowIterator
    // switch rows for columns
    def flip: Matrix[A]
    def updated(i: Int, j: Int, a: A): Matrix[A]

    // independent of other methods
    def mkString: String = {
      val sb = new StringBuilder
      for (i <- 1 to rows) {
        for (j <- 1 to columns) {
          sb.append(get(i, j))
          if (j < columns) {
            sb.append(" ")
          }
        }
        if (i < rows) {
          sb.append("\n")
        }
      }
      sb.toString()
    }
  }
  //TODO sparse matrix variant
  protected[math] case class VectorMatrix[A](m: Int, n: Int, data: Vector[A]) extends Matrix[A] {
    require(data.length == m * n, s"Vector has length ${data.size} but is expected to have ${m * n}")
    val size: Size = (m, n)

    def map[B](f: A => B): Matrix[B] = new VectorMatrix(m, n, data.map(f))

    // i in m, j in n
    def get(i: Int, j: Int): A = data(toVectorIndex(i, j))

    def iterator: Iterator[A] = data.iterator

    def flip: Matrix[A] = {
      def swap(i: Int, j: Int): Vector[A] = {
        val lf = new ListBuffer[A]
        // this will pick indices in order of matrix swaped
        for (a <- 0 until j; b <- 0 until i) {
          lf += data(a + b * j)
        }
        lf.toVector
      }
      VectorMatrix(n, m, swap(m, n))
    }

    private def toVectorIndex(i: Int, j: Int): Int = (i - 1) * n + (j - 1)

    def rowIterator: Iterator[Iterator[A]] = data.sliding(n, n).map(_.iterator)
    def updated(i: Int, j: Int, a: A): Matrix[A] = new VectorMatrix(m, n, data.updated(toVectorIndex(i, j), a))
  }

  //TODO generalize Numeric types
  implicit class IntMatrixOps(m: Matrix[Int]) {
    def +(that: Matrix[Int]): Matrix[Int] = Matrix.map2(m, that)(_ + _)
    def *(a: Int): Matrix[Int] = m.map(a * _)
    def *(that: Matrix[Int]): Matrix[Int] = if (m.columns != that.rows) {
      throw new IllegalArgumentException(s"Size of matrices are not multiplicative a=${m.size}, b=${that.size}")
    } else {
      val lf = new ListBuffer[Int]
      val y = for (r <- m.rowIterator) {
        // TODO performance toVector evaluation
        val rv = r.toVector
        for (c <- that.columnIterator) {
          val cv = c.toVector
          lf += (rv dot cv)
        }
      }
      //TODO preserve type of matrix
      VectorMatrix(m.rows, that.columns, lf.toVector)

    }
  }

  implicit class IntVectorOps(v: Vector[Int]) {
    def dot(that: Vector[Int]): Int = if (v.size != that.size) {
      throw new IllegalArgumentException(s"Size of vectors are not equal a=${v.size}, b=${that.size}")
    } else {
      v.zip(that).map(p => p._1 * p._2).sum
    }
  }

  object Matrix {

    def map2[A, B, C](ma: Matrix[A], mb: Matrix[B])(f: (A, B) => C): Matrix[C] = if (ma.size != mb.size) {
      throw new IllegalArgumentException(s"Size of matrices are not equal a=${ma.size}, b=${mb.size}")
    } else {
      //TODO preserve type of matrix
      val mappedData = ma.iterator.zip(mb.iterator).map(f.tupled).toVector
      VectorMatrix(ma.rows, ma.columns, mappedData)
    }

    // creation
    def fillSquare[A](n: Int, a: A): Matrix[A] = fill(n, n, a)

    def fill[A](m: Int, n: Int, a: A): Matrix[A] = VectorMatrix(m, n, Vector.fill(m * n)(a))

    def create[A](m: Int, n: Int)(a: A*): Matrix[A] = VectorMatrix(m, n, a.toVector)

    // todo maybe sparse
    def diagonal[A](m: Int, diag: A, zero: A): Matrix[A] = (1 to m).foldLeft(fillSquare(m, zero))((a, i) => a.updated(i, i, diag))

    // computation


  }

}
