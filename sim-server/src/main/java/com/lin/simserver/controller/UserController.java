//package com.lin.simserver.controller;
//
//import com.lin.sim.chat.session.Session;
//import com.lin.sim.common.ResultDto;
//import com.lin.sim.entity.resp.OnLineUserResp;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * TODO
// *
// * @author lin
// * @version 1.0
// * @date 2021/7/12 14:45
// */
//@Api(tags = "聊天用户")
//@RestController
//@RequestMapping("/user")
//@CrossOrigin
//public class UserController {
//    @Autowired
//    private Session session;
//
//    @GetMapping("/online")
//    public ResultDto<List<OnLineUserResp>> findOnlineUser(@RequestParam("token") String token){
//        return new ResultDto<>(session.findOnlineUser(token));
//    }
//
//}
