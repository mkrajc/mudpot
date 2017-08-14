package math

object Groups {

  trait Group[T] extends Traversable[T] {
    def unit: T
    def op(a: T, b: T): T
    def inverse(a: T): T
    def contains(a: T): Boolean
  }

  object GroupLaw {
    def identity[A](group: Group[A]): Boolean = {
      val verify = group.filterNot(a => group.contains(group.op(a, group.unit)))
      println(s"Identity Law [${verify.isEmpty}] for group G=$group")
      verify.isEmpty
    }

    def inverse[A](group: Group[A]): Boolean = {
      val verify = group.forall(a => {
        val i = group.inverse(a)
        group.contains(i) && group.op(a, i) == group.unit
      })
      println(s"Inverse Law ${verify} for group G=$group ")
      verify
    }
  }

  implicit class GroupOps[A](group: Group[A]) {
    def hasSubgroup(h: Group[A]): Boolean = h.forall(group.contains)

    def isSubgroupOf(g: Group[A]): Boolean = group.forall(g.contains)

    def hasNormalSubgroup(h: Group[A]): Boolean =
      group.forall(g => {
        h.forall(a => {
          val g_ = group.inverse(g)
          val ga= group.op(g, a)
          val gag = group.op(ga, g_ )
          //println(s"gag-1($g, $a, $g_) = $gag")
          h.contains(gag)
        })
      })

    def pow(a: A, n: Int): A = n match {
      case 0 => group.unit
      case 1 => a
      case x => (0 to (n - 2)).foldLeft(a)((b, n) => group.op(b, a))
    }

    def order(a: A): Int = {
      def next(b: A, a: A, n: Int): Int = {
        val ba = group.op(b, a)
        if (ba == group.unit) n
        else next(ba, a, n + 1)
      }
      next(a, a, 2)
    }

  }

  object OneIntMultiGroup extends Group[Int] {
    override def unit: Int = 1
    override def inverse(a: Int): Int = 1
    override def contains(a: Int): Boolean = a == 1
    override def op(a: Int, b: Int): Int = a * b
    override def foreach[U](f: (Int) => U): Unit = f(1)
  }

  abstract class SetGroup[T](val unit: T, set: Set[T]) extends Group[T] {
    override def foreach[U](f: T => U): Unit = set.foreach(f)
    override def contains(a: T): Boolean = set.contains(a)
  }

  case class Z(mod: Int) extends SetGroup(0, (0 to (mod - 1)).toSet) {
    override def op(a: Int, b: Int): Int = (a + b) % mod
    override def inverse(a: Int): Int = (mod - a) % mod
  }

  def main(args: Array[String]) {
    GroupLaw.identity(OneIntMultiGroup)
    GroupLaw.inverse(OneIntMultiGroup)
    val z8 = Z(8)
    val z4 = Z(4)

    z8.foreach(i => println(i + " x " + z8.inverse(i) + " = " + z8.op(i, z8.inverse(i))))
    GroupLaw.identity(z8)
    GroupLaw.inverse(z8)

    println(z8.pow(1, 8))

    z8.foreach(i => println(i + " = " + z8.order(i)))


    GroupLaw.identity(z4)
    GroupLaw.inverse(z4)

    println(z8.hasSubgroup(z4))
    println(z8.hasNormalSubgroup(z4))

  }
}

