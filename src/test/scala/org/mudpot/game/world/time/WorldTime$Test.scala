package org.mudpot.game.world.time

import org.mudpot.game.world.time.WorldTime.{Date, Time}
import org.mudpot.utils.EnumUtils.CyclicEnum
import org.scalatest.{FlatSpec, Matchers}


class WorldTime$Test extends FlatSpec with Matchers {

  behavior of "WorldTime Date"

  object Months extends CyclicEnum {

    type M_ = M
    val F = M(2)
    val S = M(3)
    val T = M(4)

    case class M private[Months](days: Int) extends Val {

    }

  }

  it should "create correct date when adding small amount of time" in {

    val date = new Date(Time(0, 0), 0, 0)

    println(date)


  }
}
