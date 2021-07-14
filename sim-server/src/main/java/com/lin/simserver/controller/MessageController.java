package com.lin.simserver.controller;

import com.alibaba.fastjson.JSON;
import com.lin.simserver.common.ResultDto;
import com.lin.simserver.constant.MsgStatus;
import com.lin.simserver.entity.Message;
import com.lin.simserver.entity.User;
import com.lin.simserver.entity.req.MessageSuccessReq;
import com.lin.simserver.entity.req.SendMsgDto;
import com.lin.simserver.entity.req.SendMsgReq;
import com.lin.simserver.entity.resp.MessageResp;
import com.lin.simserver.entity.resp.UserResp;
import com.lin.simserver.repository.MessageRepository;
import com.lin.simserver.repository.UserRepository;
import com.lin.simserver.utils.AscUtils;
import com.lin.simserver.utils.RedisUtils;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 14:17
 */
@Api(tags = "消息")
@RestController
@RequestMapping("/msg")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping
    public ResultDto<List<MessageResp>> findMessage(@RequestParam("currentUserId") String currentUserId,@RequestParam("friendUserId") String friendUserId){
        User currentUser = userRepository.findByUserId(currentUserId);
        User friendUser = userRepository.findByUserId(friendUserId);

        Map<String,User> userMap = new HashMap<>();
        userMap.put(currentUser.getUserId(),currentUser);
        userMap.put(friendUser.getUserId(),friendUser);

        List<Message> messages = messageRepository.findByFromUserIdAndToUserId(currentUserId, friendUserId);
        List<Message> messages2 = messageRepository.findByFromUserIdAndToUserId(friendUserId, currentUserId);
        messages.addAll(messages2);
        List<MessageResp> messageResps = messages.stream().sorted((o1,o2)-> o1.getId() - o2.getId()).map(v -> {
            MessageResp messageResp = new MessageResp();
            BeanUtils.copyProperties(v, messageResp);

            UserResp toUserResp = new UserResp();
            BeanUtils.copyProperties(userMap.get(v.getToUserId()),toUserResp);
            messageResp.setToUser(toUserResp);

            UserResp fromUserResp = new UserResp();
            BeanUtils.copyProperties(userMap.get(v.getFromUserId()),fromUserResp);
            messageResp.setFromUser(fromUserResp);

            return messageResp;
        }).collect(Collectors.toList());
        return ResultDto.ok(messageResps);
    }


    @PostMapping("/send")
    public ResultDto<Message> sendMsg(@RequestBody SendMsgReq msgReq){
        Message message = saveMsg(msgReq);

        User fromUser = userRepository.findByUserId(msgReq.getFromUserId());
        String fromToken = AscUtils.generateToken(fromUser);

        User toUser = userRepository.findByUserId(msgReq.getToUserId());
        String toToken = AscUtils.generateToken(toUser);


        SendMsgDto sendMsgDto = new SendMsgDto();
        sendMsgDto.setMessageId(message.getId());
        sendMsgDto.setFromToken(fromToken);
        sendMsgDto.setToToken(toToken);
        sendMsgDto.setContent(msgReq.getContent());
        // 消息发送订阅  可改 mq
        redisUtils.sendMsgToRedis("sendMessage", sendMsgDto);
        return new ResultDto<>(message);
    }

    @PostMapping("/sendSuccess")
    public Boolean sendMsgSuccess(@RequestBody MessageSuccessReq messageSuccessReq){
        messageRepository.updateMessage(MsgStatus.SUCCESS,new Date(),messageSuccessReq.getMessageId());
        return true;
    }

    private Message saveMsg(SendMsgReq msgReq) {
        Message message = new Message();
        message.setFromUserId(msgReq.getFromUserId());
        message.setToUserId(msgReq.getToUserId());
        message.setContent(msgReq.getContent());
        message.setStatus(MsgStatus.SENDING);
        message.setCreateDate(new Date());
        return messageRepository.save(message);
    }

}
