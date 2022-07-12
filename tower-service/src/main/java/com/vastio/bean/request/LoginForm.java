package com.vastio.bean.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 登录表单
 *
 * @author xlch
 * @Date 2018-02-23 10:22
 */
@Data
public class LoginForm {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @Override
    public String toString() {
        return "LoginForm{" +
                "username='" + username + '\'' +
                '}';
    }
}
