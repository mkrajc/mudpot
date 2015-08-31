package org.mudpot.location

import org.scalatest.{FlatSpec, Matchers}

class PathTest extends FlatSpec with Matchers{

  "Path" should "build correct string from parts" in {
    val p = new Path(List("1","2","3"))
    p.toString should be("/1/2/3")
  }

  it should "build correct string from empty parts" in {
    val p = new Path(Nil)
    p.toString should be("/")
  }

  it should "build correct path for appended part" in {
    val p = new Path(List("1","2","3"))
    val nested = p.append("4")
    nested.toString should be("/1/2/3/4")
  }

  it should "build correct path for prepend part" in {
    val p = new Path(List("1","2","3"))
    val nested = p.prepend("0")
    nested.toString should be("/0/1/2/3")
  }

  it should "get correct parent" in {
    val p = new Path(List("1","2","3"))
    val parent = p.parent
    parent.toString should be("/1/2")
  }

  it should "get correct parent from empty" in {
    val p = new Path(Nil)
    val parent = p.parent
    parent.toString should be("/")
  }

  it should "take correct path" in {
    val p = new Path(List("1","2","3"))
    val first = p.take(1)
    first.toString should be("/1")
  }

  it should "create hash for path" in {
    val p = new Path(List("veryVeryLong","reallyVeryVeryLong","reallyReallySomeLongPath"))
    p.unique.length should be(40)
  }

  it should "have same hash for same paths" in {
    val p1 = new Path(List("veryVeryLong","reallyVeryVeryLong","reallyReallySomeLongPath"))
    val p2 = new Path(List("veryVeryLong","reallyVeryVeryLong","reallyReallySomeLongPath"))
    p1.unique should be(p2.unique)
  }
}
