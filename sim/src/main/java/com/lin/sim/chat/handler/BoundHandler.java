package com.lin.sim.chat.handler;

import com.lin.sim.chat.entity.Message;
import com.lin.sim.chat.entity.MessageResp;
import com.lin.sim.chat.session.Session;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 15:22
 */
@Component("bound")
public class BoundHandler implements HandlerEngine{
    @Autowired
    private Session session;

    @Override
    public void process(ChannelHandlerContext ctx, Message msg) {
        HashMap<String, String> data = msg.getData();
        String user = data.get("from");
        session.online(ctx.channel(),user);
        // 上线通知所有用户（除本身外）
        session.noticenAllChannel(ctx.channel());
    }
}
