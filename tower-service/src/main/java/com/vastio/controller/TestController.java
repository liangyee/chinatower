package com.vastio.controller;

import com.vastio.bean.model.User;
import com.vastio.controller.base.BaseController;
import com.vastio.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试类
 *
 * @author xlch
 * @Date 2018-03-02 13:11
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    private UserService userService;

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        List<User> users = userService.findAll();
        return users;
    }
}
