package com.lin.sim.chat.entity;

import lombok.Data;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/12 16:12
 */
@Data
public class MessageResp {
    private String key;
    private Integer messageId;
    private String fromUserId;
    private String fromUserName;
    private Object content;

}
