package com.lin.simserver.entity.resp;

import lombok.Data;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 15:02
 */
@Data
public class UserResp {
    private String id;
    private String userId;
    private String userName;
    private String password;
    private String avatar;
}
