/**
 * @author 60417
 * @date 2022/1/16
 * @time 17:00
 * @todo
 */
package com.yuyefanhua.blogsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.io.IOException;


@Configuration
//组件扫描的包位置
//@ComponentScan(basePackages = {"com.yuyefanhua.blogsystem.web"})
//启用Spring MVC
@EnableWebMvc
//extends WebMvcConfigurerAdapter 过时，推荐使用：implement
public class WebConfig implements WebMvcConfigurer {
    //静态资源的处理，交给默认的tomcat servlet处理
    // DefaultServletHandlerConfigurer的enable方法将交给Servlet容器的默认的Servlet处理
    //而不是DispatcherServlet本身来处理
    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {   //配置拦截器
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/admin")
//                .excludePathPatterns("/admin/login");
//    }
    /**
     * 静态资源的位置配置，放过js和css、images的文件
     * 不拦截相关请求
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //需要配置静态资源：
//        下级静态资源
        registry.addResourceHandler("/js/lib/**").addResourceLocations("/js/lib");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    /** 静态资源处理： 不被拦截 **/

    //我们为了使用html和thymeleaf作为视图，使用Thymeleaf的模板引擎：
    //Thymeleaf 视图解析器
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        //设置模板引擎：
        viewResolver.setTemplateEngine(templateEngine);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }
    //Thymeleaf模板引擎
    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);//设置模板解析器
//        设置thymeleaf的Spring Security方言：
//        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }
    //模板解析器 解析的视图文件
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        //创建模板引擎：
        SpringResourceTemplateResolver templateResolver
                = new SpringResourceTemplateResolver();
        //设置前后缀
        templateResolver.setPrefix("/WEB-INF/template1/");
        templateResolver.setSuffix(".html");
        //设置模板类型：
        //Template Mode 'HTML5' is deprecated. Using Template Mode 'HTML' instead.
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }
    //    配置multipart解析器：
    @Bean
    public MultipartResolver multipartResolver() throws IOException {
         return new StandardServletMultipartResolver();
    }
}
