/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:34
 * @todo
 */
package com.yuyefanhua.blobsystem.service.impl;

import com.yuyefanhua.blobsystem.domain.Comment;
import com.yuyefanhua.blobsystem.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public List<Comment> getCommentByBlogId(Long blogId) {
        return null;
    }

    @Override
    public int saveComment(Comment comment) {
        return 0;
    }
}
