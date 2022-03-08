/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:33
 * @todo
 */
package com.yuyefanhua.blogsystem.service;

import com.yuyefanhua.blogsystem.domain.Comment;

import java.util.List;

public interface CommentService {
//    通过博客id查找对应的博客的评论
    List<Comment> getCommentByBlogId(Long blogId);
//添加一条博客，传入的是comment
    int saveComment(Comment comment);
}
