package org.mudpot.game.world.time

import org.mudpot.game.world.time.WorldTime.{Date, DateFormatter, Time}


trait Calendar[M] {

  def months(): List[M]

  def days(month: M): Int

  def month(index: Int): M

  def formatter(format: String): DateFormatter

  def addTime(date: Date, time: Time): Date

}

