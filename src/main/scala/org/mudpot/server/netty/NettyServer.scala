package org.mudpot.server.netty

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.GlobalEventExecutor
import org.mudpot.game.world.WorldTime
import org.mudpot.server.netty.telnet.TelnetServerConfig
import sun.security.acl.WorldGroupImpl

class NettyServer(config: ServerConfig) {
  val serverBootstrap = configure(config)

  def run(): Unit = {
    println("starting server")
    // Start the server.
    serverBootstrap.bind(config.address).sync
    println("STARTED")
  }

  def stop(): Unit = {
    println("stopping server")
    serverBootstrap.group().shutdownGracefully().sync()
    println("STOPPED")

  }

  protected def configure(config: ServerConfig): ServerBootstrap = {
    val sb = new ServerBootstrap()
    sb.group(config.group, config.workerGroup)
      .channelFactory(config.channelFactory)
      .handler(config.handler)
      .childHandler(config.childHandler)
  }

}

object NettyServer {
  def main(args: Array[String]) {
    val config: TelnetServerConfig = new TelnetServerConfig
    val server = new NettyServer(config)


    try {
      val t = new WorldTime
      t.run(config.workerGroup)

      server.run()

    }
    catch {
      case t: Throwable =>
        t.printStackTrace(System.out)
        server.stop()
    }
  }
}
