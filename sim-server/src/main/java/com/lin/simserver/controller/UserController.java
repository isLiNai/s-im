package com.lin.simserver.controller;

import com.lin.simserver.common.ResultDto;
import com.lin.simserver.entity.User;
import com.lin.simserver.entity.resp.UserResp;
import com.lin.simserver.repository.UserRepository;
import com.lin.simserver.utils.AscUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 15:01
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResultDto<UserResp> findUser(@RequestParam("token") String token){
        String decrypt = AscUtils.decrypt(token);
        String[] arr = decrypt.split("-");
        User user = userRepository.findByUserNameAndPassword(arr[1], arr[2]);
        UserResp userResp = new UserResp();
        BeanUtils.copyProperties(user,userResp);
        return new ResultDto<>(userResp);
    }

}
