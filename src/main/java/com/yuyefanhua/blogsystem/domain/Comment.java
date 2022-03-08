/**
 * @author 60417
 * @date 2022/2/18
 * @time 21:13
 * @todo
 */
package com.yuyefanhua.blogsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 评论实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private String nickname;//昵称
    private String email;//邮件
    private String content;//内容
    private boolean adminComment;  //是否为管理员评论

    private String avatar;//头像
    private Date createTime;//创建时间

    private Long blogId;//所评论的博客id
    private Long parentCommentId;  //父评论id
    private String parentNickname;

    //回复评论
    //private List<Comment> replyComments = new ArrayList<>();

    //父评论
    private Comment parentComment;

    private Blog blog;//所评论的博客，需要通过博客id来写入

}
