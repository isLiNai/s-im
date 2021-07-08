package com.lin.sim.chat.coder;

import com.lin.sim.chat.coder.decoder.MsgDecoder;
import com.lin.sim.chat.coder.encoder.MsgEncoder;
import com.lin.sim.chat.handler.SimHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * 初始化管道处理类
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 10:36
 */
public class SimChannelInitHandler extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(60000))
                .addLast(new WebSocketServerProtocolHandler("/sim"))
                .addLast(new MsgDecoder())
                .addLast(new SimHandler())
                .addLast(new MsgEncoder());
    }
}
