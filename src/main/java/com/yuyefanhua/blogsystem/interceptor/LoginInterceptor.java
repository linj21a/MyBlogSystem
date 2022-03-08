/**
 * @author 60417
 * @date 2022/3/3
 * @time 16:07
 * @todo
 */
package com.yuyefanhua.blogsystem.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，可以尝试鉴权
 */

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if (request.getSession().getAttribute("user") == null){
                System.out.println("拦截器登录拦截");
                System.out.println(request.getRequestURI());
                response.sendRedirect(request.getContextPath()+"/admin");//重新登录
                return false;
            }
            return true;

    }
}
