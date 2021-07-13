package com.lin.simserver.entity.req;

import lombok.Data;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 14:27
 */
@Data
public class SendMsgReq {
    private String fromUserId;
    private String toUserId;
    private String content;
}
