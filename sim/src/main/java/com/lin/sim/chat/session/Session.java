package com.lin.sim.chat.session;

import com.lin.sim.chat.entity.MessageResp;
import com.lin.sim.entity.resp.OnLineUserResp;
import com.lin.sim.utils.AscUtils;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 15:12
 */
public class Session {
    //用户id=>channel示例
    private final ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();
    AttributeKey<String> key = AttributeKey.valueOf("user");

    /**
     * 判断一个通道是否有用户在使用
     * 可做信息转发时判断该通道是否合法
     * @param channel
     * @return
     */
    public boolean hasUser(Channel channel) {
        AttributeKey<String> key = AttributeKey.valueOf("user");
        return (channel.hasAttr(key) || channel.attr(key).get() != null);//netty移除了这个map的remove方法,这里的判断谨慎一点
    }

    /**
     * 上线一个用户
     *
     * @param channel
     * @param userId
     */
    public void online(Channel channel, String userId) {
        //先判断用户是否在web系统中登录?
        //这部分代码个人实现,参考上面redis中的验证
        this.channelMap.put(userId, channel);
        channel.attr(key).set(userId);
    }

    /**
     *  上线通知所有用户（除本身外）
     * @param currentChannel
     */
    public void noticenAllChannel(Channel currentChannel){
        // 当前用户提示绑定成功
        MessageResp boundMessage = new MessageResp();
        boundMessage.setKey("bound");
        boundMessage.setContent("绑定成功");
        currentChannel.writeAndFlush(boundMessage);

        // 其他用户提示有用户上线通知
        MessageResp onlineMessage = new MessageResp();
        onlineMessage.setKey("online");
        onlineMessage.setContent("有用户上线");
        Enumeration<String> keys = this.channelMap.keys();
        String userToken = currentChannel.attr(key).get();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            if(!StringUtils.equals(userToken,key)){
                Channel channel = this.channelMap.get(key);
                channel.writeAndFlush(onlineMessage);
            }
        }
    }

    /**
     * 根据用户id获取该用户的通道
     *
     * @param userId
     * @return
     */
    public Channel getChannelByUserId(String userId) {
        return this.channelMap.get(userId);
    }

    /**
     * 判断一个用户是否在线
     *
     * @param userId
     * @return
     */
    public Boolean online(String userId) {
        return this.channelMap.containsKey(userId) && this.channelMap.get(userId) != null;
    }

    public Boolean offline(Channel channel){
        AttributeKey<String> key = AttributeKey.valueOf("user");
        String userToken = channel.attr(key).get();
        channelMap.remove(userToken);
        return true;
    }


    public List<OnLineUserResp> findOnlineUser(){
        Enumeration<String> keys = this.channelMap.keys();
        List<OnLineUserResp> onLineUserResps = new ArrayList<>();
        while(keys.hasMoreElements()){
            String token = keys.nextElement();
            String decrypt = AscUtils.decrypt(token);
            String[] split = decrypt.split("-");
            OnLineUserResp onLineUserResp = new OnLineUserResp();
            onLineUserResp.setUserName(split[1]);
            onLineUserResp.setPassword(split[2]);
            onLineUserResps.add(onLineUserResp);
        }
        return onLineUserResps;
    }

}
