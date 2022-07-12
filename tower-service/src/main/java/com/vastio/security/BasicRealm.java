package com.vastio.security;

import com.vastio.bean.model.User;
import com.vastio.service.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * shiro 自定义 realm
 *
 * @author xlch
 * @Date 2018-02-22 15:02
 */
public class BasicRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicRealm.class);

    @Autowired
    private UserService userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = principals.getPrimaryPrincipal().toString();
        User user1 = userService.findByName(username);
        if (null != user1) {
            List<String> roles = new ArrayList<>();
            roles.add(user1.getRole());
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.addRoles(roles);
            return authorizationInfo;
        }
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        User user1 = userService.findByName(username);
        if (null != user1) {
            return new SimpleAuthenticationInfo(user1.getUsername(), user1.getPassword(), this.getName());
        }
        return null;
    }
}
