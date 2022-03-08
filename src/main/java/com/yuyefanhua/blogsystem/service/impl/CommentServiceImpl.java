/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:34
 * @todo
 */
package com.yuyefanhua.blogsystem.service.impl;

import com.yuyefanhua.blogsystem.dao.BlogDao;
import com.yuyefanhua.blogsystem.dao.CommentDao;
import com.yuyefanhua.blogsystem.domain.Comment;
import com.yuyefanhua.blogsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BlogDao blogDao;

    @Override
    public List<Comment> getCommentByBlogId(Long blogId) {  //查询父评论
        //没有父节点的默认为-1
        return commentDao.findByBlogIdAndParentCommentNull(blogId, Long.parseLong("-1"));
    }

    @Override
    //接收回复的表单
    public int saveComment(Comment comment) {
        //获得父id
        Long parentCommentId = comment.getParentComment().getId();
        //没有父级评论默认是-1
        if (parentCommentId != -1) {
            //有父级评论
            comment.setParentComment(commentDao.findByParentCommentId(comment.getParentCommentId()));
        } else {
            //没有父级评论
            comment.setParentCommentId((long) -1);
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.saveComment(comment);
    }
}
