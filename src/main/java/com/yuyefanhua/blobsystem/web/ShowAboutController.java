/**
 * @author 60417
 * @date 2022/2/18
 * @time 22:18
 * @todo
 */
package com.yuyefanhua.blobsystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class ShowAboutController {
    /**
     * 关于我 映射
     * @return
     */
    @RequestMapping(value = "/about",method = RequestMethod.GET)
    public String aboutMe(){
        return "about";
    }
}
