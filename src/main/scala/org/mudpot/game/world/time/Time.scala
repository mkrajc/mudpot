package org.mudpot.game.world.time

class Time(val hour: Int, val minutes: Int) {
  override def toString: String = s"$hour:$minutes"
}
