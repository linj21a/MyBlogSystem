/**
 * @author 60417
 * @date 2022/3/1
 * @time 11:18
 * @todo
 */
package com.yuyefanhua.blobsystem.web.admin;

import com.yuyefanhua.blobsystem.domain.User;
import com.yuyefanhua.blobsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 * * 实现管理员登录，并且需要使用session 设置为70s过期
 *
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 登录界面 视图层返回
     * @param session
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginPage(HttpSession session){
        System.out.println("login1");
//        if(session.getAttribute("user")!=null){
//            //判断session是否有效
//            return "admin/index";
//        }
        System.out.println("login2");
        return "admin/login";//登录界面的获取
    }

    /**
     * 登录功能
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(
            @RequestParam String username, @RequestParam String password,
            HttpSession session, RedirectAttributes attributes){
        System.out.println("login post");
        //登录，需要用户名、密码，同时可以利用session来进行判断，上一次登录是否过期
        System.out.println(username+"   "+password);
        //1、用户校验
        User user = userService.checkUser(username, password);

        if(user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);//存到session域
            return "admin/index";
        }else {
            attributes.addFlashAttribute("msg","用户名或密码错误");
            return "redirect:/admin/login";//重新来过
        }
    }

    /**
     * 注销功能
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.removeAttribute("user");//删除user，退出登录
        return "redirect:/admin/login";//回到登录界面
    }
}
