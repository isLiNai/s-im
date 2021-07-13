package com.lin.sim.chat.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.lin.sim.chat.entity.Message;
import com.lin.sim.chat.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 10:39
 */
@Slf4j
public class SimHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        Map<String, HandlerEngine> handlerEngineMap = SpringUtil.getBeansOfType(HandlerEngine.class);
        HandlerEngine handlerEngine = handlerEngineMap.get(msg.getKey());
        if(handlerEngine == null){
            return;
        }
        handlerEngine.process(ctx,msg);
    }

    /**
     * 用户下线 通知其他用户
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Session session = SpringUtil.getBean(Session.class);
        session.offline(ctx.channel());
        session.noticenOfflineAllChannel();
        super.channelUnregistered(ctx);
    }
}
