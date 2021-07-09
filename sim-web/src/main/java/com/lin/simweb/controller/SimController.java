package com.lin.simweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/9 9:37
 */
@Controller
public class SimController {

    @RequestMapping("/home")
    public String home(){
        return "home";
    }
}
