package com.vastio.service;

import com.vastio.bean.model.User;
import com.vastio.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务层
 *
 * @author xlch
 * @Date 2018-03-02 13:35
 */
@Service
public class UserService {

    private UserMapper userMapper;

    @Resource
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findByName(String username) {
        return userMapper.findByName(username);
    }
}
