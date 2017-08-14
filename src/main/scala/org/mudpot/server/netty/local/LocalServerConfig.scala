package org.mudpot.server.netty.local

import java.net.SocketAddress

import io.netty.bootstrap.ChannelFactory
import io.netty.channel._
import io.netty.channel.local.{LocalAddress, LocalChannel, LocalEventLoopGroup, LocalServerChannel}
import org.mudpot.server.netty.{EngineChannelHandler, ServerConfig}
import org.mudpot.text.engine.GlobalEngineFactory


class LocalServerConfig extends ServerConfig {
  private val localGroup = new LocalEventLoopGroup

  override def address: SocketAddress = new LocalAddress("local")

  override def childHandler: ChannelHandler = new ChannelInitializer[LocalChannel] {
    override def initChannel(ch: LocalChannel): Unit = {
      ch.pipeline().addFirst(new EngineChannelHandler(GlobalEngineFactory))
    }
  }

  override def handler: ChannelHandler = new ChannelInitializer[LocalServerChannel] {
    override def initChannel(ch: LocalServerChannel): Unit = {
    }
  }

  override def group: EventLoopGroup = localGroup

  override def channelFactory: ChannelFactory[_ <: ServerChannel] = new ChannelFactory[LocalServerChannel] {
    override def newChannel(): LocalServerChannel = new LocalServerChannel
  }

  override def workerGroup: EventLoopGroup = localGroup
}
