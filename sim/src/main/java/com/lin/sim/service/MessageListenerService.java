package com.lin.sim.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.sim.chat.session.Session;
import com.lin.sim.entity.SendMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 14:35
 */
@Slf4j
@Service
public class MessageListenerService {
    @Autowired
    private Session session;
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public void handleMessage(String jsonStr) throws JsonProcessingException {
        log.info(jsonStr);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SendMsgDto sendMsgDto = objectMapper.readValue(jsonStr, SendMsgDto.class);
        log.info("消息发送订阅频道有新消息进来，参数：{}", sendMsgDto);
        session.sendMessage(sendMsgDto);
        log.info("消息发送成功：{}", sendMsgDto.getToToken());
    }

}
