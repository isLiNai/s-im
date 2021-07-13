package com.lin.sim.chat.session;

import com.lin.sim.chat.constant.ImNotice;
import com.lin.sim.chat.entity.MessageResp;
import com.lin.sim.entity.SendMsgDto;
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
    public void noticenOnlineAllChannel(Channel currentChannel){
        // 当前用户提示绑定成功
        MessageResp boundMessage = new MessageResp();
        boundMessage.setKey(ImNotice.BOUND);
        boundMessage.setContent("绑定成功");
        currentChannel.writeAndFlush(boundMessage);

        // 其他用户提示有用户上线通知
        MessageResp onlineMessage = new MessageResp();
        onlineMessage.setKey(ImNotice.ONLINE);
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

    /**
     * 离线 移除用户信息
     * @param channel
     * @return
     */
    public Boolean offline(Channel channel){
        AttributeKey<String> key = AttributeKey.valueOf("user");
        String userToken = channel.attr(key).get();
        channelMap.remove(userToken);
        return true;
    }

    /**
     *  下线通知所有用户
     */
    public void noticenOfflineAllChannel(){
        // 下线通知
        MessageResp offlineMessage = new MessageResp();
        offlineMessage.setKey(ImNotice.OFFLINE);
        offlineMessage.setContent("有用户下线");
        Enumeration<String> keys = this.channelMap.keys();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            Channel channel = this.channelMap.get(key);
            channel.writeAndFlush(offlineMessage);
        }
    }

    public List<OnLineUserResp> findOnlineUser(String currentToken){
        Enumeration<String> keys = this.channelMap.keys();
        List<OnLineUserResp> onLineUserResps = new ArrayList<>();
        while(keys.hasMoreElements()){
            String token = keys.nextElement();
            if(!StringUtils.equals(token,currentToken)){
                String decrypt = AscUtils.decrypt(token);
                String[] split = decrypt.split("-");
                OnLineUserResp onLineUserResp = new OnLineUserResp();
                onLineUserResp.setUserName(split[1]);
                onLineUserResp.setPassword(split[2]);
                onLineUserResp.setUserId(split[3]);
                onLineUserResps.add(onLineUserResp);
            }
        }
        return onLineUserResps;
    }

    public boolean sendMessage(SendMsgDto sendMsgDto){
        Channel channel = this.channelMap.get(sendMsgDto.getToToken());
        String decrypt = AscUtils.decrypt(sendMsgDto.getFromToken());
        String[] split = decrypt.split("-");

        MessageResp message = new MessageResp();
        message.setKey(ImNotice.SEND);
        message.setContent(sendMsgDto.getContent());
        message.setFromUserName(split[1]);
        message.setFromUserId(split[3]);
        message.setMessageId(sendMsgDto.getMessageId());
        channel.writeAndFlush(message);
        return true;
    }


}
