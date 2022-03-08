/**
 * @author 60417
 * @date 2022/3/4
 * @time 21:55
 * @todo
 */
package com.yuyefanhua.blogsystem.Listener;

import com.yuyefanhua.blogsystem.service.ViewsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 统计次数的 创建一次seesion就说明有人访问过一次
 */
@WebListener
@Component
public class ViewListener implements HttpSessionListener {
    private ViewsService viewsService;
    /**
     * 通过WebApplicationContextUtils 得到Spring容器的实例。根据bean的名称返回bean的实例。
     * @param servletContext  ：ServletContext上下文。
     * @param beanName  :要取得的Spring容器中Bean的名称。
     * @return 返回Bean的实例。
     */
    private Object getObjectFromApplication(ServletContext servletContext, String beanName){
        //通过WebApplicationContextUtils 得到Spring容器的实例。
        WebApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        //返回Bean的实例。
        assert application != null;
        return application.getBean(beanName);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        viewsService =
                (ViewsService) this.getObjectFromApplication(
                        se.getSession().getServletContext(),
                        "viewsService");

        // 每出现一个新用户进行一次刷新
        viewsService.updateViewsForPeople();
        // 设置session过期时间，单位为秒
        se.getSession().setMaxInactiveInterval(60);

    }
}
