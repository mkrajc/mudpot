package org.mudpot.client

import java.io.{BufferedReader, InputStreamReader}

import io.netty.bootstrap.Bootstrap
import io.netty.channel.local.LocalChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel._
import io.netty.handler.logging.{LogLevel, LoggingHandler}
import org.mudpot.server.netty.NettyServer
import org.mudpot.server.netty.local.LocalServerConfig


object LocalClient {

  def main(args: Array[String]) {

    val server = new NettyServer(new LocalServerConfig)

    try {
      server.run()
    }
    catch {
      case e => server.stop()
    }

    val serverConfig = new LocalServerConfig
    val addr = serverConfig.address

    val clientGroup: EventLoopGroup = new NioEventLoopGroup
    try {

      val cb: Bootstrap = new Bootstrap
      cb.group(clientGroup).channel(classOf[LocalChannel]).handler(new ChannelInitializer[LocalChannel]() {
        @throws(classOf[Exception])
        def initChannel(ch: LocalChannel) {
          ch.pipeline.addLast(new LoggingHandler(LogLevel.DEBUG))
          ch.pipeline().addLast(new SimpleChannelInboundHandler[String]() {
            override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
              print(msg)
            }
          })
        }
      })

      val ch: Channel = cb.connect(addr).sync.channel

      System.out.println("Enter text (quit to end)")

      var lastWriteFuture: ChannelFuture = null
      val in: BufferedReader = new BufferedReader(new InputStreamReader(System.in))

      var run = true
      while (run) {
        val line: String = in.readLine
        if (line == null || "quit".equalsIgnoreCase(line)) {
          run = false
        } else {
          lastWriteFuture = ch.writeAndFlush(line)
        }
      }
      if (lastWriteFuture != null) {
        lastWriteFuture.awaitUninterruptibly
      }
    } finally {
      clientGroup.shutdownGracefully
      server.stop()
    }
  }


}
