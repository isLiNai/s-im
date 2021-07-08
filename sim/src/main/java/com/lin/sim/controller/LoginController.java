package com.lin.sim.controller;

import com.lin.sim.common.ResultDto;
import com.lin.sim.entity.req.LoginReq;
import com.lin.sim.utils.AscUtils;
import com.lin.sim.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class LoginController {

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping
    @ApiOperation(value = "登录", httpMethod = "POST")
    public ResultDto<String> login(@RequestBody LoginReq loginReq){
        // 认证
        Boolean authenticate = authenticate(loginReq);
        if(!authenticate){
            ResultDto.error(500,"认证失败");
        }
        // 生成token
        String token = generateToken(loginReq);

        // token 放入redis
        redisUtils.set(loginReq.getUserName(),token);
        return new ResultDto<>(token);
    }


    private Boolean authenticate(LoginReq loginReq){
        return true;
    }

    private String generateToken(LoginReq loginReq){
        String salt = "123";
        return AscUtils.encrypt(salt + "-" + loginReq.getUserName() + "-" + loginReq.getPassWord());
    }

}
