package org.mudpot.server.netty.telnet

import io.netty.buffer.{ByteBuf, Unpooled}
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.string.{StringDecoder, StringEncoder}
import io.netty.handler.logging.{LogLevel, LoggingHandler}
import io.netty.handler.ssl.SslContext
import org.mudpot.server.netty.ChannelGroupChannelHandler


class TelnetServerInitializer(val sslCtx: Option[SslContext]) extends ChannelInitializer[SocketChannel] {
  private val newLineRN: ByteBuf = Unpooled.wrappedBuffer(Array[Byte]('\r', '\n'))
  private val newLineN: ByteBuf = Unpooled.wrappedBuffer(Array[Byte]('\n'))


  override def initChannel(ch: SocketChannel): Unit = {
    val pipe = ch.pipeline

    sslCtx.map(ctx => pipe.addLast(ctx.newHandler(ch.alloc())))

    val ddecoder = new DelimiterBasedFrameDecoder(8192, true, true, newLineRN, newLineN)
    pipe.addLast(ddecoder)
    pipe.addLast(TelnetServerInitializer.decoder)
    pipe.addLast(TelnetServerInitializer.encoder)
    pipe.addLast(ChannelGroupChannelHandler)
    pipe.addLast(TelnetServerHandler)
    pipe.addLast(new LoggingHandler(LogLevel.DEBUG))
  }
}

object TelnetServerInitializer {
  private[telnet] val decoder = new StringDecoder()
  private[telnet] val encoder = new StringEncoder()
}
