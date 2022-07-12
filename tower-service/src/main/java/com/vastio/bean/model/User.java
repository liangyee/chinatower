package com.vastio.bean.model;

import lombok.Data;

/**
 * 用户
 *
 * @author xlch
 * @Date 2018-03-02 13:22
 */

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String role;
}
