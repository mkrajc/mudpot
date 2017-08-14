package org.mudpot
import org.scalacheck._
import org.scalacheck.{Gen, Prop, Properties}

import scala.util.Random

trait S {
  def double: (Double, S)
}

class OldSeed(seed: Long) extends S {

  protected def next(bits: Int): (Int, OldSeed) = {
    var oldSeed = seed
    var newSeed = (oldSeed * OldSeed.multiplier + OldSeed.addend) & OldSeed.mask
    do {
      oldSeed = newSeed
      newSeed = (oldSeed * OldSeed.multiplier + OldSeed.addend) & OldSeed.mask
    } while (oldSeed == newSeed)
    ((newSeed >>> (48 - bits)).toInt, new OldSeed(newSeed))
  }

  def next: OldSeed = {
    next(32)._2
  }

  def long: (Long, OldSeed) = {
    val p1 = next(32)._1.toLong << 32
    val (p2, s) = next(32)
    (p1 + p2, s)
  }

  def double: (Double, OldSeed) = {
    val next1: (Int, OldSeed) = next(27)
    val d = ((next(26)._1.toLong << 27) + next1._1) / (1L << 53).toDouble
    (d, next1._2)
  }

  def i: (Int, OldSeed) = {
    next(32)
  }
}

object OldSeed {
  val multiplier = 0x5DEECE66DL
  val addend = 0xBL
  val mask = (1L << 48) - 1
  /** Generate a deterministic seed. */
  def apply(s: Int): OldSeed = {
    new OldSeed(s)
  }

  /** Generate a random seed. */
  def random(): OldSeed = apply(Random.nextInt)

}

class NewSeed(random: Random) extends S {
  override def double: (Double, S) = {
    (random.nextDouble(), new NewSeed(random))
  }
}

object SeedSpecification extends Properties("Seed") {

  property("uniform double") = Prop.forAll(Gen.choose(1000,1000)) { n =>
    val r: OldSeed = OldSeed.random()

    //val r: S = new NewSeed(new Random())
/*
    val (seed, numbers) = 1.to(n).foldLeft((r, Vector[Double]())) {
      case ((s1, nums), _) =>
        val (d, s2) = s1.double
        //println(d)
        (s2, nums :+ d)
    }
    val avg = numbers.sum / numbers.size
    println(s"avg $avg size=${numbers.size}")
    avg >= 0.4 && avg <= 0.6
*/

    val (seed, numbers) = 1.to(n).foldLeft((r, Vector[Long]())) {
      case ((s1, nums), _) =>
        val (d, s2) = s1.long
        println(d)
        (s2, nums)
    }
    val avg = numbers.sum / numbers.size
    println(s"avg $avg size=${numbers.size}")
    false

  }
}

