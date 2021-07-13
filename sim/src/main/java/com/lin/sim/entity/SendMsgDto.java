package com.lin.sim.entity;

import lombok.Data;
import lombok.ToString;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 16:07
 */
@Data
@ToString
public class SendMsgDto {
    private Integer messageId;
    private String fromToken;
    private String toToken;
    private String content;
}
