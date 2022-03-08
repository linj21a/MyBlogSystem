/**
 * @author 60417
 * @date 2022/2/20
 * @time 21:40
 * @todo
 */
package com.yuyefanhua.blogsystem.service;

import com.yuyefanhua.blogsystem.domain.Blog;
import com.yuyefanhua.blogsystem.domain.BlogTag;

import java.util.List;
import java.util.Map;
public interface BlogService {
    //更新博客，创建博客，按照年份返回博客（归档）需要重写
    int saveBlog(Blog blog);//新增博客
    int updateBlog(Blog blog);//编辑博客
    public Map<String, List<Blog>> archiveBlog();//归档博客
    //获取博客的详细内容，需要md文件转为html格式
    Blog getDetailedBlog(Long id);
    int countBlog();

    //其余直接调用Dao的方法
    int deleteBlog(Long id);
    List<Blog> getIndexBlog();  //主页博客展示
    List<Blog> getAllBlog();
    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客
    List<Blog> getAllRecommendBlog();  //推荐博客展示
    Blog getBlog(Long id);  //后台展示博客
    List<Blog> getSearchBlog(String query);  //全局搜索博客
    List<Blog> searchAllBlog(Blog blog);  //后台根据标题、分类、推荐搜索博客
    List<Blog> getByTagId(Long tagId);  //根据标签id获取博客
    int saveBlogTag(BlogTag blogTag);
    List<String> findGroupYear();  //查询所有年份，返回一个集合
    List<Blog> findByYear(String year);  //按年份查询博客
    int getAllViews();
    void updateViews(Long id);



}
