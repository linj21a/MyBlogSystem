/**
 * @author 60417
 * @date 2022/2/18
 * @time 22:19
 * @todo
 */
package com.yuyefanhua.blobsystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 标签 按钮的
 * \tags
 */
@Controller
public class ShowTagController {
    /**
     * tags标签 映射
     * @return
     */
    @RequestMapping(value = "/tags",method = RequestMethod.GET)
    public String tags(){
        return "tags";
    }
}
