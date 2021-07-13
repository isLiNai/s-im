package com.lin.sim.config;

import com.lin.sim.service.MessageListenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 说明:
 * redis 消息监听配置
 * @author wangxingquan
 **/
@Component
public class RedisListenerConfig {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MessageListenerService messageListenerService;
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("sendMessage"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter() {
        //使用下面service中的handleMessage
        return new MessageListenerAdapter(messageListenerService, "handleMessage");
    }

}