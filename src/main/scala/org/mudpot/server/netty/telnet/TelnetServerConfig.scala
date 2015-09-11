package org.mudpot.server.netty.telnet

import java.net.{InetSocketAddress, SocketAddress}

import io.netty.bootstrap.ChannelFactory
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.{ChannelHandler, EventLoopGroup, ServerChannel}
import io.netty.handler.logging.{LogLevel, LoggingHandler}
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate
import org.mudpot.server.netty.ServerConfig


class TelnetServerConfig extends ServerConfig {
  override def address: SocketAddress = new InetSocketAddress(22)

  override def childHandler: ChannelHandler = {
    val ssc = new SelfSignedCertificate()
    val ssl = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build()
    new TelnetServerInitializer(None)
  }

  override def handler: ChannelHandler = new LoggingHandler(LogLevel.INFO)

  override def group: EventLoopGroup = new NioEventLoopGroup(1)

  override def channelFactory: ChannelFactory[_ <: ServerChannel] = new ChannelFactory[NioServerSocketChannel] {
    override def newChannel(): NioServerSocketChannel = new NioServerSocketChannel
  }

  override def workerGroup: EventLoopGroup = new NioEventLoopGroup
}
