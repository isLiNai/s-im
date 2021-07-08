package com.lin.sim.chat.coder.decoder;

import com.alibaba.fastjson.JSON;
import com.lin.sim.chat.entity.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 15:40
 */
public class MsgDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
        String messageStr = msg.text();
        Message message = JSON.parseObject(messageStr, Message.class);
        out.add(message);
    }
}