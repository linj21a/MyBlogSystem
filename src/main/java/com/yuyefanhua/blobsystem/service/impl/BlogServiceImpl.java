/**
 * @author 60417
 * @date 2022/2/20
 * @time 21:40
 * @todo
 */
package com.yuyefanhua.blobsystem.service.impl;

import com.yuyefanhua.blobsystem.dao.BlogDao;
import com.yuyefanhua.blobsystem.domain.Blog;
import com.yuyefanhua.blobsystem.domain.BlogTag;
import com.yuyefanhua.blobsystem.exception.NotBlogFoundException;
import com.yuyefanhua.blobsystem.service.BlogService;
import com.yuyefanhua.blobsystem.util.MarkDownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
@Service
//service注解表明其为一个数据仓库提供的服务，spring将会将其注入为一个单例对象
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;//自动注入
    @Override
    public int saveBlog(Blog blog) {
        return blogDao.saveBlog(blog);
    }

    @Override
    public int updateBlog(Blog blog) {
        return blogDao.updateBlog(blog);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        return null;
    }

    @Override
    public Blog getDetailedBlog(Long id) {
        //根据博客id查询blog的细节，需要查找博客的内容、标签、类型、作者等
        Blog detailedBlog = blogDao.getDetailedBlog(id);
        if(detailedBlog==null){
            throw new NotBlogFoundException("没有这样的博客"+id);
        }
        String content = detailedBlog.getContent();
        //将内容转化为html文档格式（md文件转化）
        MarkDownUtils.mardDown2Html_Expand(content,null);
        //临时测试代码：
        String path = "I:\\Spring项目\\个人博客项目.md";
        String s = null;
        try {
            s = MarkDownUtils.readMdFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(s);
        String s1 = MarkDownUtils.mardDown2Html_Expand(s,null);
        content = s1;
        //临时测试代码结束
        //需要一个工具类.
        detailedBlog.setContent(content);
        return detailedBlog;

    }

    @Override
    public int countBlog() {
        return blogDao.getAllBlog().size();
    }

    //---------------直接调用
    @Override
    public int deleteBlog(Long id) {
        return blogDao.deleteBlog(id);
    }

    @Override
    public List<Blog> getIndexBlog() {
        return blogDao.getIndexBlog();
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogDao.getAllBlog();
    }

    @Override
    public List<Blog> getByTypeId(Long typeId) {
        return blogDao.getByTypeId(typeId);
    }

    @Override
    public List<Blog> getAllRecommendBlog() {
        return blogDao.getAllRecommendBlog();
    }

    @Override
    public Blog getBlog(Long id) {
        return blogDao.getBlog(id);
    }

    @Override
    public List<Blog> getSearchBlog(String query) {//query为关键字
        return blogDao.getSearchBlog(query);
    }

    @Override
    public List<Blog> searchAllBlog(Blog blog) {
        return blogDao.searchAllBlog(blog);
    }

    @Override
    public List<Blog> getByTagId(Long tagId) {
        return blogDao.getByTagId(tagId);
    }

    @Override
    public int saveBlogTag(BlogTag blogTag) {
        return blogDao.saveBlogTag(blogTag);
    }

    @Override
    public List<String> findGroupYear() {
        return blogDao.findGroupYear();
    }

    @Override
    public List<Blog> findByYear(String year) {
        return blogDao.findByYear(year);
    }

    @Override
    public int getAllViews() {
        return blogDao.getAllViews();
    }

    @Override
    public void updateViews(Long id) {
        blogDao.updateViews(id);
    }
}
