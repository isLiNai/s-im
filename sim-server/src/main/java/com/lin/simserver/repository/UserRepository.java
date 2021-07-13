package com.lin.simserver.repository;

import com.lin.simserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 11:41
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserNameAndPassword(String userName,String password);

    User findByUserId(String userId);

}
