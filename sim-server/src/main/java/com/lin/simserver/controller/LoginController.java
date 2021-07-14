package com.lin.simserver.controller;

import com.lin.simserver.common.ResultDto;
import com.lin.simserver.entity.User;
import com.lin.simserver.entity.req.LoginReq;
import com.lin.simserver.repository.UserRepository;
import com.lin.simserver.utils.AscUtils;
import com.lin.simserver.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 14:25
 */
@Api(tags = "登录")
@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserRepository userRepository;


    @PostMapping
    @ApiOperation(value = "登录", httpMethod = "POST")
    public ResultDto<String> login(@RequestBody LoginReq loginReq){
        // 认证
        User user = authenticate(loginReq);
        if(user == null){
            return ResultDto.error(500,"认证失败");
        }
        // 生成token
        String token = AscUtils.generateToken(user);
        // token 放入redis
//        redisUtils.set(loginReq.getUserName(),token);
        return new ResultDto<>(token);
    }


    private User authenticate(LoginReq loginReq){
        User user = userRepository.findByUserNameAndPassword(loginReq.getUserName(), loginReq.getPassWord());
        return user;
    }



}
