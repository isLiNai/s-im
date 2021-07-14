package com.lin.simserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/13 14:12
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fromUserId;
    private String toUserId;
    private Integer status;
    private String content;
    private Date createDate;
    private Date updateDate;
}
