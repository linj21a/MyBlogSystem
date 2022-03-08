/**
 * @author 60417
 * @date 2022/3/1
 * @time 11:18
 * @todo
 */
package com.yuyefanhua.blogsystem.web.admin;

import com.yuyefanhua.blogsystem.domain.User;
import com.yuyefanhua.blogsystem.service.TokenService;
import com.yuyefanhua.blogsystem.service.UserService;
import com.yuyefanhua.blogsystem.util.CheckCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
    @Autowired
    private TokenService tokenService;
    @GetMapping(value = "/login")
    public String loginPage(){return "admin/login";}
    /**
     * 登录界面 视图层返回
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String login(HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException {
        //在这里会检验它是否已经登录，以及是否需要重新登录
        //1、获取它携带的cookie，看是否存在这样的token
        System.out.println("获取登录界面");
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(int i=0;i<cookies.length;i++){
                String cookiename = URLDecoder.decode(cookies[i].getName(), "utf-8");
                System.out.println("cookie_name:"+cookiename);
                if(cookiename.equals("usertoken")){
                    //redis里面有没有：过不过期等等
                    String token = URLDecoder.decode(cookies[i].getValue(), "utf-8");
                    boolean check = tokenService.check(token,session);
                    if(check){//登录成功，无需登录，session里面已经存放有user的信息了
                        System.out.println("用户无需重新登录");
                        return "admin/index";//
                    }
                }
            }
        }
        System.out.println("用户尚未登录，或者登录过期");
        return "redirect:/admin/login";//登录界面的获取
    }

    /**
     * 登录功能
     * @param username
     * @param password
     * @param session
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(
            @RequestParam String username, @RequestParam String password,
            HttpSession session, RedirectAttributes redirectAttributes,
            HttpServletRequest request, HttpServletResponse response){
        System.out.println("用户登录请求");
        String code = request.getParameter("checkcode");
        //验证码校验：
        boolean flag = CheckCodeUtils.checkCode(code,session);//这里已经移除session里面的验证码(过期)
       if(!flag){
           //model的信息带不过去，因为重定向，需要使用redirectAttributes flash属性
           redirectAttributes.addFlashAttribute("msg","验证码有误");
            return "redirect:/admin/login";//重新来过
        }
        //用户校验
        User user = userService.checkUser(username, password);
        if(user!=null){
            System.out.println("登录成功");
            user.setPassword(null);
            session.setAttribute("user",user);//存到session域  这里使用token所以user不要放到session里面了
            String userAgent = request.getHeader("user-agent");//获取用户的user-agent域
            String token = this.tokenService.generateToken(userAgent, user);
            this.tokenService.saveToken(token, user);//保存token到redis里面
            //把它存放到cookie里面
            //cookie采取同源策略，只有相同域名的网页才能获取域名对应的cookie,别人获取不到我们的cookie
            //token应该在HTTP的头部发送从而保证了Http请求无状态
            //CSRF攻击的原因是浏览器会自动带上cookie，而浏览器不会自动带上token
            Cookie cookie = new Cookie("usertoken",token);
            cookie.setMaxAge(2*60*60*1000);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            System.out.println("登录成功!!!!!!!!!");
            //成功登录
            return "/admin/index";
        }else {
            redirectAttributes.addFlashAttribute("msg","用户名或密码错误");
//            model.addAttribute("msg","用户名或密码错误");
            return "redirect:/admin/login";//回到登录界面
        }
    }

    /**
     * 注销功能
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpSession session){
        System.out.println("注销登录--------");
        tokenService.delToken((User) session.getAttribute("user"));
        session.removeAttribute("user");//删除user，退出登录
        //同时token等都直接设置未过期：
        return "redirect:admin/login";//回到登录界面
    }
}
