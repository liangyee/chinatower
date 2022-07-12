package com.vastio.config;

import com.vastio.security.ApiFilter;
import com.vastio.security.BasicRealm;
import com.vastio.security.SessionManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用配置
 *
 * @author xlch
 * @Date 2018-02-22 14:32
 */
@Configuration
public class AppConfig {
    @Bean
    public SessionManager sessionManager() {
        return new SessionManager();
    }

    @Bean
    public WebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm());
        SessionManager sessionManager = new SessionManager();
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }
    /* ///////////////////////////////
   /*     shiro   配置             /
   /*/ ////////////////////////////
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        DelegatingFilterProxy filter = new DelegatingFilterProxy();
        filter.setTargetFilterLifecycle(true);
        FilterRegistrationBean bean = new FilterRegistrationBean(filter);
        bean.addUrlPatterns("/*");
        bean.setName("shiroFilter");
        return bean;
    }

    @Bean(name = "api")
    public ApiFilter apiFilter() {
        return new ApiFilter();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter() throws IOException {
        Resource resource = new ClassPathResource("auth.properties");
        String authConfig = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
        Map<String, Filter> filterMap = new HashMap<>();
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(this.securityManager());
        shiroFilter.setFilterChainDefinitions(authConfig);
        shiroFilter.setFilters(filterMap);
        return shiroFilter;
    }

//    @Bean
//    public DefaultWebSecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(this.realm());
//        return securityManager;
//    }

    @Bean
    public Realm realm() {
        BasicRealm realm = new BasicRealm();
        realm.setCredentialsMatcher(new HashedCredentialsMatcher("MD5"));
        return realm;
    }

}
