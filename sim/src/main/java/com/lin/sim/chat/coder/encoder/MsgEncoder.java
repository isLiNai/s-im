package com.lin.sim.chat.coder.encoder;

import com.alibaba.fastjson.JSON;
import com.lin.sim.chat.entity.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 14:56
 */
public class MsgEncoder extends MessageToMessageEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        String s = JSON.toJSONString(msg);
        out.add(s);
    }

}
