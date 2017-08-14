package org.mudpot.server.netty

import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.util.AttributeKey
import org.mudpot.text.engine.{Engine, EngineFactory}


class EngineChannelHandler(val factory: EngineFactory) extends SimpleChannelInboundHandler[String] {
  val ENGINE: AttributeKey[Engine] = AttributeKey.valueOf("TextEngine");

  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    var engine = ctx.attr(ENGINE).get()

    if (engine == null) {
      engine = factory.create("username")
      ctx.attr(ENGINE).setIfAbsent(engine)
    }

    ctx.writeAndFlush(engine.handle(msg))

  }
}
