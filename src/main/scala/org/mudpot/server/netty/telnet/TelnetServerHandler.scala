package org.mudpot.server.netty.telnet

import java.net.InetAddress
import java.util.Date

import io.netty.channel.ChannelHandler.Sharable

import io.netty.channel.{ChannelFutureListener, ChannelHandlerContext, SimpleChannelInboundHandler}

@Sharable
object TelnetServerHandler extends SimpleChannelInboundHandler[String] {
  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    ctx.flush()
  }

  override def channelRead0(ctx: ChannelHandlerContext, request: String): Unit = {
    var close = false
    var response: String = ""
    if (request.isEmpty) {
      response = "Please type something.\r\n"
    } else if ("bye".equals(request.toLowerCase)) {
      response = "Have a good day!\r\n"
      close = true
    } else {
      response = "Did you say '" + request + "'?\r\n"
    }

    // We do not need to write a ChannelBuffer here.
    // We know the encoder inserted at TelnetPipelineFactory will do the conversion.
    val future = ctx.write(response);

    // Close the connection after sending 'Have a good day!'
    // if the client has sent 'bye'.
    if (close) {
      future.addListener(ChannelFutureListener.CLOSE);
    }
  }


  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n")
    ctx.write("It is " + new Date() + " now.\r\n")
    ctx.flush()
  }


  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
    cause.printStackTrace();
    ctx.close();
  }


}








