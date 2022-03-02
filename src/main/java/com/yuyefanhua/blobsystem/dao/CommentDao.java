/**
 * @author 60417
 * @date 2022/2/18
 * @time 22:08
 * @todo
 */
package com.yuyefanhua.blobsystem.dao;

import com.yuyefanhua.blobsystem.domain.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDao {
    //根据创建时间倒序来排 根据博客的id和博客评论的id
    List<Comment> findByBlogIdAndParentCommentNull(@Param("blogId") Long blogId,@Param("blogParentId") Long blogParentId);

    //查询父级对象 根据父评论的id
    Comment findByParentCommentId(Long parentcommentid);

    //添加一个评论
    int saveComment(Comment comment);
    //删除评论
    int deleteComment(Comment comment);
}
