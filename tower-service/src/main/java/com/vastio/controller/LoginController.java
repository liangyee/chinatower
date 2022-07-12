package com.vastio.controller;


import com.vastio.bean.base.ResponseResult;
import com.vastio.bean.request.LoginForm;
import com.vastio.controller.base.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 登入登出
 *
 * @author xlch
 * @Date 2018-02-22 18:00
 */
@RestController
@RequestMapping("/api")
public class LoginController extends BaseController {

    @PostMapping(value = "/login")
    public ResponseResult<Map> login(@RequestBody LoginForm loginForm, BindingResult result) {
        if (result.hasErrors()) {
            return error("用户信息不合法", 400);
        }
        Map<String, Object> res = new HashMap<>();
        String username = loginForm.getUsername();
        String pd = loginForm.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(username, pd);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            res.put("token", subject.getSession().getId());
        } catch (UnknownAccountException u) {
            return error("该用户不存在", 400);
        } catch (IncorrectCredentialsException i) {
            return error("密码与账号不匹配", 400);
        } catch (AuthenticationException a) {
            return error("登录信息不正确", 400);
        } catch (Exception e) {
            return error("服务内部错误", 400);
        }
        return successResult(res, "login success");
    }

    @GetMapping(value = "/logout")
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            currentUser.logout();
        }
    }

}
