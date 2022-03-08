/**
 * @author 60417
 * @date 2022/2/21
 * @time 12:21
 * @todo
 */
package com.yuyefanhua.blogsystem.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuyefanhua.blogsystem.domain.Blog;
import com.yuyefanhua.blogsystem.domain.Tag;
import com.yuyefanhua.blogsystem.domain.Type;
import com.yuyefanhua.blogsystem.domain.User;
import com.yuyefanhua.blogsystem.service.BlogService;
import com.yuyefanhua.blogsystem.service.TageService;
import com.yuyefanhua.blogsystem.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    private BlogService blogService;//使用@Service进行注入,service单例
    @Autowired
    private TypeService typeService;
    @Autowired
    private TageService tageService;
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String do_test(Model model){
        Blog blog = blogService.getBlog(3L);
        blog.init();//将博客的tagsId转为“1,2,3”的字符串
        System.out.println("标签id集合："+blog.getTagIds());
        String input_tags = blog.getInput_tags();
        System.out.println("input_tags:"+input_tags);
        model.addAttribute("blog",blog);
        setTypeAndTag(model);
        return "admin/ubde";
    }
    /**
     *保存类型和标签
     * @param model
     */
    public void setTypeAndTag(Model model) {
        //这里会把类型和tags放到model里面
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tageService.getAllTag());
    }
    @RequestMapping(value = "/blogs",method = RequestMethod.GET)//我们将分页拉取blogs进行展示
    public String blogs(
            @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
            Model model){

        //调用BlogService来显示
        List<Blog> allBlogs = blogService.getAllBlog();
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlogs);//分页工具
        model.addAttribute("pageInfo",pageInfo);
        ////这里会把类型和tags放到model里面
        setTypeAndTag(model);  //查询类型和标签
        return "admin/blogs";//admin下的blogs.html为视图
    }
    @RequestMapping(value = "/blogs/search",method = RequestMethod.POST)
    public String searchBlogs(
            Blog blog,
            @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
            Model model){
        System.out.println("搜索博客");
        System.out.println(blog);
//Blog{id=null, title='实战', content='null', firstPicture='null', flag='null', views=null, appreciation=null, shareStatement=null, commentabled=null, published=null, recommend=null, createTime=null,
// updateTime=null, typeId=null, userId=null, type=空, user=yuyefanhua, tagIds='null', description='null'}

        System.out.println("type.id "+blog.getTypeId()+" title:"+blog.getTitle());
        System.out.println("开始搜索");
        //开启分页：
        PageHelper.startPage(pagenum,5);//每次查询5个，默认从第一页查起
        List<Blog> allBlog = blogService.searchAllBlog(blog);//搜索所有的博客
        System.out.println("搜索到的博客：");
        System.out.println(allBlog);
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlog);
        model.addAttribute("pageInfo",pageInfo);//返回的分页对象将会按照title和type的形式形成分页
        System.out.println("查到的数量："+pageInfo.getSize());
        model.addAttribute("message","查询成功");
        setTypeAndTag(model);
        //返回的博客还没有获取其type和tage //设置标签 和类型（model）
        return "admin/blogs";
    }
    @RequestMapping(value = "/blogs/input",method = RequestMethod.GET) //新增博客的界面
    public String toAddBlog(Model model){
        model.addAttribute("blog",new Blog());//创建一个blog,前端将得到 th:object
        setTypeAndTag(model);
        //设置便签
        //设置标签 和类型（model）
        return "admin/blogs-input";//新建博客界面

    }
    //需要传递博客的id然后根据该id打开博客编辑其基本属性（title等等）
    //使用的是路径变量的方式
    @RequestMapping(value = "/blogs/{id}/input",method = RequestMethod.GET)
    public String toEditBlog(
            @PathVariable Long id,Model model
    ){
        Blog blog = blogService.getBlog(id);
        blog.init();//将博客的tagsId转为“1,2,3”的字符串
        System.out.println("编辑博客"+blog);
        model.addAttribute("blog",blog);
        setTypeAndTag(model);
        //设置标签 和类型（model）
        return "admin/blogs-input";//编辑博客界面
    }
    @GetMapping("/blogs/{id}/delete")  //get方法的requestMapping
    public String deleteBlogs(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/blogs";
    }

    //在这个界面能够提交post请求的则说明是新增博客或者编辑博客的提交数据
    //toEdit和toAdd都在这个处理
    @PostMapping("/blogs")
    public String addBlog(Blog blog, HttpSession session,
                          RedirectAttributes attributes){
        //保存博客：需要检查是否是当前的session
        System.out.println(blog);
        blog.setUser((User)session.getAttribute("user"));
        blog.setUserId(blog.getUser().getId());
        blog.setFlag(blog.getFlag());
        //设置博客的类型，前端进行了非空校验
        Type type = blog.getType();//这个type可能是选中的值或者手动输入的值
        if(type==null){
            //未知类型 0L实际不存在
            type = new Type(0L,"未知类型",null);
        }
        //判断是原有的值还是新输入的值  只返回1个
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1==null){//不存在
            long maxTypeId = typeService.maxTypeId();
            type.setId(maxTypeId+1);
            typeService.saveType(type);
        }else {
            //这个类型是存在的：
            type.setId(type1.getId());
        }
        blog.setType(type);
        blog.setTypeId(type.getId());

        //content处理

        //设置标签
        String tagIds = blog.getTagIds();
//        System.out.println("原生的tagids："+(tagIds==null?"tagIds空":tagIds));
//        System.out.println("选择的标签和输入的标签："+blog.getInput_tags());
        //tagIds是原有的标签属性，getInput_tags是获取的原有标签和新增加的标签属性
        List<Tag> tagByString = tageService.getTagByString(tagIds);
//        手动输入的标签内容，需要添加一个校验
        String[] split = blog.getInput_tags().split("#");//这里最多只有三个
        int taglength = split.length;
        if(split.length>3){//标签只允许三个
            taglength = 3;
        }
        List<Tag> newTags = new ArrayList<>(3);
        long maxId = tageService.getMaxId();
        for (int i = 0; i < taglength; i++) {
            String s = split[i].trim();
            Tag newTag = null;
            for (Tag t : tagByString) {
                if (t.getName().equals(s)) {//已经有的
                    newTag = t;//设置它的id
                    break;
                }
            }
            if(newTag==null){//数据库没有的
                newTag = new Tag(++maxId, s, null);
                //这里的tag  size不一定不重复
                tageService.saveTag(newTag);//保存
            }
            //否则数据库本身就有：
            newTags.add(newTag);
        }
        blog.setTags(newTags);//设置到博客上面
        //四大标志位非空校验
        check(blog);
        if (blog.getId() == null) {   //id为空，则为新增
            blogService.saveBlog(blog);
        } else {
            blogService.updateBlog(blog);
        }
        attributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/admin/blogs";
    }

    /**
     * //四大标志位非空校验
     * @param blog
     */
    public static void check(Blog blog){
        if(blog==null){
            return;
        }
        if(blog.getFirstPicture()==null){
            blog.setFirstPicture("");
        }
        if(blog.getFlag()==null){
            blog.setFlag("原创");
        }
        //四大标志位的非空校验：
        if(blog.getFlag().equals("转载")){
            blog.setShareStatement(true);
        }else{
            blog.setShareStatement(false);
        }
        if(blog.getRecommend()==null){
            blog.setRecommend(true);
        }
        if(blog.getAppreciation()==null){
            blog.setAppreciation(true);
        }
        if(blog.getCommentabled()==null){
            blog.setCommentabled(true);
        }
    }
}
