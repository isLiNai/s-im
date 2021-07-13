package com.lin.sim.chat.handler;

import com.lin.sim.chat.constant.ImHandler;
import com.lin.sim.chat.constant.ImNotice;
import com.lin.sim.chat.entity.Message;
import com.lin.sim.chat.entity.MessageResp;
import com.lin.sim.chat.session.Session;
import com.lin.sim.common.ResultDto;
import com.lin.sim.entity.resp.OnLineUserResp;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 寻找在线用户
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 15:22
 */
@Component(ImHandler.FIND_ONLINE_USER)
public class FindOnlineUserHandler implements HandlerEngine{
    @Autowired
    private Session session;

    @Override
    public void process(ChannelHandlerContext ctx, Message msg) {
        HashMap<String, String> data = msg.getData();
        String currentToken = data.get("token");
        List<OnLineUserResp> onlineUsers = session.findOnlineUser(currentToken);
        MessageResp messageResp = new MessageResp();
        messageResp.setKey(ImNotice.FIND_ONLINE_USER);
        messageResp.setContent(ResultDto.ok(onlineUsers));
        ctx.channel().writeAndFlush(messageResp);
    }
}
