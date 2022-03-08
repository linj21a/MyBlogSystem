/**
 * @author 60417
 * @date 2022/2/18
 * @time 22:22
 * @todo
 */
package com.yuyefanhua.blogsystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 友链
 */
@Controller
public class ShowlinksController {
    /**
     * 友情链接 映射
     * @return
     */
    @RequestMapping(value = "/links",method = RequestMethod.GET)
    public String links(){
        return "links";
    }
}
