package com.lin.simserver.entity.req;

import lombok.Data;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/14 16:24
 */
@Data
public class RegisterUser {
    private String userName;
    private String password;
}
