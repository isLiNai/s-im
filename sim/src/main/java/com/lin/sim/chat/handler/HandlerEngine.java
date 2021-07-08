package com.lin.sim.chat.handler;

import com.lin.sim.chat.entity.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 15:23
 */
public interface HandlerEngine {

    void process(ChannelHandlerContext ctx, Message msg);

}
