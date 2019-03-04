package com.tpadsz.after.netty;

import com.tpadsz.after.utils.PropertiesUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;


/**
 * 多人聊天例子服务器
 *
 * @author lkj41110
 * @version time：2017年1月16日 下午9:54:55
 */
public class ServerMain {


    private static Logger logger = Logger.getLogger(ServerMain.class);
    private static String port = PropertiesUtils.getValue("socket.port");
//    public static void main(String[] args) {
//        new ServerMain().service();
//    }

    private void service() {
        EventLoopGroup acceptor = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        final ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024); //设置TCP相关信息
        bootstrap.option(ChannelOption.ALLOW_HALF_CLOSURE, true);
        bootstrap.group(acceptor, worker);//设置循环线程组，前者用于处理客户端连接事件，后者用于处理网络IO
        bootstrap.channel(NioServerSocketChannel.class);//用于构造socketchannel工厂
        bootstrap.handler(new ChatServerHandler());
        bootstrap.childHandler(new ServerInitialHandler());//为处理accept客户端的channel中的pipeline添加自定义处理函数
        try {
            // 服务器绑定端口监听
            final ChannelFuture bind = bind(bootstrap, Integer.valueOf(port));
            Channel channel = bind.sync().channel();
            logger.info("server start running in port:" + port);
            // 监听服务器关闭监听
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("InterruptedException:"+e.getMessage());
        } finally {
            // 退出
            acceptor.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
    private ChannelFuture bind(final ServerBootstrap serverBootstrap, final int port) {
        return serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                logger.info("端口[" + port + "]绑定成功!");
                return;
            }
        });
    }
}
