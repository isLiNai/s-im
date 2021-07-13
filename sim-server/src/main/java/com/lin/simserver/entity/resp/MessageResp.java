package com.lin.simserver.entity.resp;

import java.util.Date;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 14:18
 */
public class MessageResp {
    private String id;
    private String fromUserId;
    private String toUserId;
    private Integer status;
    private String content;
    private Date createDate;
    private Date updateDate;
}
