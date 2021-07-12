package com.lin.sim.chat.coder.encoder;

import com.alibaba.fastjson.JSON;
import com.lin.sim.chat.entity.Message;
import com.lin.sim.chat.entity.MessageResp;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 14:56
 */
public class MsgEncoder extends MessageToMessageEncoder<MessageResp> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageResp msg, List<Object> out) throws Exception {
        String msgStr = JSON.toJSONString(msg);
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(msgStr);
        out.add(textWebSocketFrame);
    }

}
