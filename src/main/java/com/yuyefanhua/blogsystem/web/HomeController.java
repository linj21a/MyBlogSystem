/**
 * @author 60417
 * @date 2022/2/17
 * @time 19:17
 * @todo
 */
package com.yuyefanhua.blogsystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 主页处理
 */
@Controller
@RequestMapping(value = {"/","/yuyefanhua"})
public class HomeController {
    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model){
        model.addAttribute("jsonPath",
                "static_resource/js/lib/live2d-widget-model-shizuku/assets/shizuku.model.json");
        return "index";
    }

    /**
     * 看板娘
     * @param model
     * @return
     */
    @RequestMapping(value = "/kanbanniang",method = RequestMethod.GET)
    public String kanbanniang(Model model){
        return "kanbanniang";
    }
    /**
     * 后台登录界面 需要鉴权 login
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
}
