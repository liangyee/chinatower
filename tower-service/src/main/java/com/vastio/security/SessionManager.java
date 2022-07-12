package com.vastio.security;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author 陈晓宇
 * @version 创建时间：2017年9月20日 下午3:59:47 类说明
 */
public class SessionManager extends DefaultWebSessionManager {
    private static final Logger log = LoggerFactory.getLogger(DefaultWebSessionManager.class);

    /**
     * 重写获取sessionId的方法调用当前Manager的获取方法
     * 
     * @param request 请求
     * @param response 返回
     * @return Serializable
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        return this.getReferencedSessionIdChild(request, response);
    }

    /**
     * 获取sessionId从请求中
     * 
     * @param request 请求
     * @param response 返回
     * @return Serializable
     */
    private Serializable getReferencedSessionIdChild(ServletRequest request, ServletResponse response) {
        String id = this.getSessionIdCookieValueChild(request, response);
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "cookie");
        } else {
            id = this.getUriPathSegmentParamValueChild(request, "JSESSIONID");
            if (id == null) {
                // 获取请求头中的session
                String authorization = "Authorization";
                id = WebUtils.toHttp(request).getHeader(authorization);
                if (id == null) {
                    String name = this.getSessionIdNameChild();
                    id = request.getParameter(name);
                    if (id == null) {
                        id = request.getParameter(name.toLowerCase());
                    }
                }
            }
            if (id != null) {
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "url");
            }
        }

        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,
                    Boolean.TRUE);
        }

        return id;
    }

    // copy super
    private String getSessionIdCookieValueChild(ServletRequest request, ServletResponse response) {
        if (!this.isSessionIdCookieEnabled()) {
            log.debug("Session ID cookie is disabled - session id will not be acquired from a request cookie.");
            return null;
        } else if (!(request instanceof HttpServletRequest)) {
            log.debug("Current request is not an HttpServletRequest - cannot get session ID cookie.  Returning null.");
            return null;
        } else {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            return this.getSessionIdCookie().readValue(httpRequest, WebUtils.toHttp(response));
        }
    }

    private String getUriPathSegmentParamValueChild(ServletRequest servletRequest, String paramName) {
        if (!(servletRequest instanceof HttpServletRequest)) {
            return null;
        } else {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String uri = request.getRequestURI();
            if (uri == null) {
                return null;
            } else {
                int queryStartIndex = uri.indexOf(63);
                if (queryStartIndex >= 0) {
                    uri = uri.substring(0, queryStartIndex);
                }

                int index = uri.indexOf(59);
                if (index < 0) {
                    return null;
                } else {
                    String token = paramName + "=";
                    uri = uri.substring(index + 1);
                    index = uri.lastIndexOf(token);
                    if (index < 0) {
                        return null;
                    } else {
                        uri = uri.substring(index + token.length());
                        index = uri.indexOf(59);
                        if (index >= 0) {
                            uri = uri.substring(0, index);
                        }

                        return uri;
                    }
                }
            }
        }
    }

    private String getSessionIdNameChild() {
        String name =
                this.getSessionIdCookie() != null ? this.getSessionIdCookie().getName() : null;
        if (name == null) {
            name = "JSESSIONID";
        }

        return name;
    }
}
