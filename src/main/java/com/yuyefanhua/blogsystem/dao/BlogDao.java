/**
 * @author 60417
 * @date 2022/2/18
 * @time 22:00
 * @todo
 */
package com.yuyefanhua.blogsystem.dao;

import com.yuyefanhua.blogsystem.domain.Blog;
import com.yuyefanhua.blogsystem.domain.BlogTag;

import java.util.List;

public interface BlogDao{
    int deleteBlog(Long id);
    List<Blog> getIndexBlog();  //主页博客展示
    List<Blog> getAllBlog();
    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客
    List<Blog> getAllRecommendBlog();  //推荐博客展示
    Blog getDetailedBlog(Long id);  //博客详情
    Blog getBlog(Long id);  //后台展示博客
    int getAllViews();
    void updateViews(Long id);
    List<Blog> getSearchBlog(String query);  //全局搜索博客
    List<Blog> searchAllBlog(Blog blog);  //后台根据标题、分类、推荐搜索博客
    List<Blog> getByTagId(Long tagId);  //根据标签id获取博客

    int updateBlog(Blog blog);
    int saveBlog(Blog blog);
    int saveBlogTag(BlogTag blogTag);
    List<String> findGroupYear();  //查询所有年份，返回一个集合
    List<Blog> findByYear(String year);  //按年份查询博客










}
