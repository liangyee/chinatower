package com.vastio.security;

import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限控制器
 *
 * @author xlch
 * @Date 2018-02-22 15:35
 */
public class ApiFilter extends UserFilter {

    private static final String ERROR_JSON = "{\"code\":401,\"url\":\"%s\"}";

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (response instanceof HttpServletResponse) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().printf(ERROR_JSON, httpServletRequest.getRequestURI());
            return false;
        }

        return super.onAccessDenied(request, response);
    }
}
