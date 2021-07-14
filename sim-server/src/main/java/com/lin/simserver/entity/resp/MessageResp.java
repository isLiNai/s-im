package com.lin.simserver.entity.resp;

import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 14:18
 */
@Data
public class MessageResp {
    private Integer id;
    private UserResp fromUser;
    private UserResp toUser;
    private Integer status;
    private String content;
    private Date createDate;
    private Date updateDate;
}
