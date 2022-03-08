/**
 * @author 60417
 * @date 2022/3/3
 * @time 15:59
 * @todo
 */
package com.yuyefanhua.blogsystem.Aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 配置日志切面
 *
 */
@Aspect
@Component
//开启aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LogAspect {
//    slf4j的实现
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//切入点是指向控制器时
    @Pointcut("execution(* com.yuyefanhua.blogsystem.web.admin.*.*(..))")        //定义切入点表达式
    public void log(){}

    @Before("log()")    //引用切入点 调用log方法前使用
    public void doBefore(JoinPoint joinPoint){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //获得类名.方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //获得方法参数
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        //打印请求信息
        logger.info("Request: {}", requestLog);
    }

    @After("log()")
    public void doAfter(){
        logger.info("------------doAfter------------");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result){
        //打印返回值
        logger.info("Result: {}", result);
    }

    @Data
    @AllArgsConstructor
    public static class RequestLog{      //用于封装请求信息
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }
}
