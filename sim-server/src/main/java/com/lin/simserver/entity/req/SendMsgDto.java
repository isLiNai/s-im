package com.lin.simserver.entity.req;

import lombok.Data;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 16:07
 */
@Data
public class SendMsgDto {
    private Integer messageId;
    private String fromToken;
    private String toToken;
    private String content;
}
