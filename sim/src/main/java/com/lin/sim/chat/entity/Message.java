package com.lin.sim.chat.entity;

import lombok.Data;

import java.util.HashMap;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 11:06
 */
@Data
public class Message {
    private String key;
    private final HashMap<String, String> data = new HashMap();
}
