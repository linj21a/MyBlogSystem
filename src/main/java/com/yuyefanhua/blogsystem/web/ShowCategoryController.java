/**
 * @author 60417
 * @date 2022/2/18
 * @time 22:18
 * @todo
 */
package com.yuyefanhua.blogsystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class ShowCategoryController {
    /**
     * 分类 映射
     * @return
     */
    @RequestMapping(value = "/categories",method = RequestMethod.GET)
    public String categories(){
        return "categories";
    }
}
