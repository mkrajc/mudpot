package org.mudpot.game.world

import java.util.Date
import java.util.concurrent.{ScheduledExecutorService, TimeUnit}

class WorldTime extends Runnable {

  override def run(): Unit = {
    println(new Date())
    // ChannelGroupChannelHandler.channels.writeAndFlush(new Date().toString)
  }

  def run(scheduler: ScheduledExecutorService): Unit = {
    scheduler.scheduleAtFixedRate(this, 0, 15, TimeUnit.SECONDS)
  }

}
