package com.lin.simserver;

import com.lin.simserver.entity.User;
import com.lin.simserver.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SimServerApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void jpaTest() {
        List<User> all = userRepository.findAll();
        System.out.println(all);
    }

}
