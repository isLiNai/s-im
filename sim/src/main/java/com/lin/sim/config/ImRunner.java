package com.lin.sim.config;

import com.lin.sim.chat.server.ImServer;
import com.lin.sim.chat.session.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 10:04
 */
@Slf4j
@Component
public class ImRunner implements ApplicationRunner {

    @Value("${websocket.port}")
    private Integer socketPort;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new ImServer(socketPort).init().startUp();
    }

    @Bean
    public Session session(){
        return new Session();
    }

}
