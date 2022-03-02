/**
 * @author 60417
 * @date 2022/2/21
 * @time 12:21
 * @todo
 */
package com.yuyefanhua.blobsystem.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuyefanhua.blobsystem.domain.Blog;
import com.yuyefanhua.blobsystem.domain.User;
import com.yuyefanhua.blobsystem.service.BlogService;
import com.yuyefanhua.blobsystem.service.TageService;
import com.yuyefanhua.blobsystem.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 必须管理员才能够操作blog。非管理员只能够查看和评论
 *
 * 包括的功能有：
 * * 插入新的blog
 * * 删除blog
 * * 更新blog
 * *
 *
 * blog需要type、tage、
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    //1、需要注入一个blogService
    @Autowired
    private BlogService blogService;//使用@Service进行注入,service必须存在的一个
    @RequestMapping(value = "/blogs",method = RequestMethod.GET)//我们将分页拉取blogs进行展示
    public String blogs(
            @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
            Model model){

        //调用BlogService来显示
        List<Blog> allBlogs = blogService.getAllBlog();
        //这里得到的博客：
        /*
        Blog{id=1, title='实战1', content='null', firstPicture='null',
        flag='null', views=null, appreciation=false, shareStatement=false,
        commentabled=false, published=true, recommend=true, createTime=null,
        updateTime=Sat Feb 12 11:07:43 CST 2022, typeId=1, userId=null,
        type=com.yuyefanhua.blobsystem.domain.Type@587c0940,
        user=com.yuyefanhua.blobsystem.domain.User@197a0d82,
        tagIds='null', description='null'}
        * */
        System.out.println(allBlogs.toString());

        Blog detailedBlog = blogService.getDetailedBlog(1L);
        System.out.println("细节："+detailedBlog.getContent());
        model.addAttribute("blogtest",detailedBlog);
        //
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlogs);//分页工具
        model.addAttribute("pageInfo",pageInfo);
        //返回的博客还没有获取其type和tage  //设置标签 和类型（model）
//        return "admin/blogs";//admin下的blogs.html为视图
        return "admin/test";
    }
    @RequestMapping(value = "/blogs/search",method = RequestMethod.POST)
    public String searchBlogs(
            Blog blog,
            @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
            Model model){
        //开启分页：
        PageHelper.startPage(pagenum,5);//每次查询5个，默认从第一页查起
        List<Blog> allBlog = blogService.getAllBlog();
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlog);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("message","查询成功");
        //返回的博客还没有获取其type和tage //设置标签 和类型（model）
        return "admin/blogs";
    }
    @RequestMapping(value = "/blogs/input",method = RequestMethod.GET) //新增博客的界面
    public String toAddBlog(Model model){
        model.addAttribute("blog",new Blog());//创建一个blog
        //设置便签
        //设置标签 和类型（model）
        return "admin/blogs-input";//新建博客界面

    }
    //需要传递博客的id然后根据该id打开博客编辑其基本属性（title等等）
    @RequestMapping(value = "/blogs/{id}/input",method = RequestMethod.GET)
    public String toEditBlog(
            @PathVariable Long id,Model model
    ){
        Blog blog = blogService.getBlog(id);
        blog.init();//将博客的tagsId转为“1,2,3”的字符串
        model.addAttribute("blog",blog);
        //设置标签 和类型（model）
        return "admin/blogs-input";//编辑博客界面
    }
    @GetMapping("/blogs/{id}/delete")  //get方法的requestMapping
    public String deleteBlogs(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/blogs";
    }
    @Autowired
    private TypeService typeService;
    @Autowired
    private TageService tageService;

    //在这个界面能够提交post请求的则说明是新增博客或者编辑博客的提交数据
    //toEdit和toAdd都在这个处理
    @PostMapping("/blogs")
    public String addBlog(Blog blog, HttpSession session,
                          RedirectAttributes attributes){
        //保存博客：需要检查是否是当前的session
        blog.setUser((User)session.getAttribute("user"));
        blog.setUserId(blog.getUser().getId());
        blog.setFlag(blog.getFlag());//这里？？
        //设置博客的类型
        blog.setType(typeService.getType(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //给blog中的List<Tag>赋值
//        blog.setTags(tageService.getTagByString(blog.getTagIds()));
        if (blog.getId() == null) {   //id为空，则为新增
            blogService.saveBlog(blog);
        } else {
            blogService.updateBlog(blog);
        }

        attributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/admin/blogs";
    }




}
