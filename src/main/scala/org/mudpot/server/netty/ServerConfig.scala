package org.mudpot.server.netty

import java.net.SocketAddress

import io.netty.bootstrap.ChannelFactory
import io.netty.channel.{ServerChannel, ChannelHandler, EventLoopGroup}

trait ServerConfig{
  def address: SocketAddress

  def group: EventLoopGroup

  def workerGroup: EventLoopGroup

  def channelFactory: ChannelFactory[_ <: ServerChannel]

  def childHandler: ChannelHandler

  def handler: ChannelHandler
}
