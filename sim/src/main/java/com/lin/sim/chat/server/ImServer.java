package com.lin.sim.chat.server;

import com.lin.sim.chat.coder.SimChannelInitHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 10:19
 */
@Slf4j
public class ImServer {
    private Integer port;
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    // 是否执行初始化操作
    private Boolean ifInit = false;

    private ImServer(){}

    public ImServer(Integer port){
        this.port = port;
    }

    public ImServer init(){
        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        serverBootstrap.group(bossGroup,workerGroup);
        this.ifInit = true;
        return this;
    }

    public void startUp(){
        if(!ifInit){
            log.error("ImServer uninitialized!");
            return;
        }

        serverBootstrap.channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new SimChannelInitHandler());
        try {
            Channel channel = serverBootstrap.bind(port).sync().channel();
            log.info("ImServer started on port: "+port+" (webSocket)");
            channel.closeFuture().addListener(ChannelFutureListener.CLOSE);
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
