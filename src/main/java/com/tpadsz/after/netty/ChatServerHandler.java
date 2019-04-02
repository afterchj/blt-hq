package com.tpadsz.after.netty;

import com.tpadsz.after.bo.BltManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

/**
 * 服务器主要的业务逻辑
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {


    private Logger logger = Logger.getLogger(ChatServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String msg) throws Exception {
        Channel channel = arg0.channel();
        channel.writeAndFlush(msg);
        logger.info("[" + channel.remoteAddress() + "]: " + msg);
        BltManager.saveMap(msg, channel.remoteAddress().toString());
    }

    //在建立链接时发送信息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress() + "] " + "online");
    }

    //退出链接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress() + "] " + "offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("[" + ctx.channel().remoteAddress() + "]" + "exit the room");
        ctx.close().sync();
    }
}
