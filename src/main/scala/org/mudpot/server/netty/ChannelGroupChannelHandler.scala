package org.mudpot.server.netty

import io.netty.buffer.{Unpooled, ByteBuf}
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.group.{ChannelMatchers, DefaultChannelGroup}
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.util.concurrent.GlobalEventExecutor

@Sharable
case object ChannelGroupChannelHandler extends SimpleChannelInboundHandler[String] {
  val channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)

  override def channelRegistered(ctx: ChannelHandlerContext): Unit = {

    channels.add(ctx.channel())
    ctx.fireChannelRegistered()
    val ar: Array[Byte] = Array(255.toByte, 253.toByte, 1.toByte)

    ctx.writeAndFlush(Unpooled.copiedBuffer(ar))
    println("channelRegistered")
  }

  override def channelUnregistered(ctx: ChannelHandlerContext): Unit = {
    println("channelUnregistered")
    channels.remove(ctx.channel())
    ctx.fireChannelUnregistered()
  }


  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    val me = ChannelMatchers.is(ctx.channel())
    val others = ChannelMatchers.invert(me)
    channels.writeAndFlush("[you] " + msg + "\r\n", me)
    channels.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + "\r\n", others);

    if ("bye".equals(msg.toLowerCase)) {
      ctx.channel().close()
    }
  }
}
