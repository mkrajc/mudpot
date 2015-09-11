import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LocalEchoClient extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Print as received
        System.out.println(this + " " + msg);
        if (msg.equals("test")) {
            ctx.fireChannelRead(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
