package org.mudpot.server.netty

import java.net.SocketAddress

import io.netty.bootstrap.ChannelFactory
import io.netty.channel.local.{LocalAddress, LocalChannel, LocalEventLoopGroup, LocalServerChannel}
import io.netty.channel._


class LocalServerConfig extends ServerConfig {
  private val localGroup = new LocalEventLoopGroup

  override def address: SocketAddress = new LocalAddress("local")

  override def childHandler: ChannelHandler = new ChannelInitializer[LocalChannel] {
    override def initChannel(ch: LocalChannel): Unit = {
      ch.pipeline().addFirst(new ChannelInboundHandlerAdapter {
        override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {

        }
      })
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
