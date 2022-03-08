/**
 * @author 60417
 * @date 2022/2/18
 * @time 21:07
 * @todo
 */
package com.yuyefanhua.blogsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客 pojo
 *
 * data 简化getter setter
 * 下面两个注解生成无参构造器和全参构造器
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    private Long id;//博客id
    private String title;//标题
    private String content;//内容
    private String firstPicture;//文件第一张图片
    private String flag;//标记原创还是啥
    private Integer views;//有多少人看过
    private Boolean appreciation;//赞赏
    private Boolean shareStatement;//是否被分享
    private Boolean commentabled;//是否允许评论
    private Boolean published;//是否已经发布（后台）
    private Boolean recommend;//推荐?
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

    //用于接收博客输入的标签：
    private String input_tags;// #号隔开，只允许用户输入


    //这两个id可以用来进行表链接查询得到相关数据
    private Long typeId;//博客类型id
    private Long userId;//用户id
    private Type type;

    private User user;
    //获取多个标签的id
    private String tagIds;//标签id，以,号间隔的字符串
    private String description;//描述  或者前言

    private List<Tag> tags = new ArrayList<>();//这个博客所带的标签信息

    private List<Comment> comments = new ArrayList<>();//这个博客所带的评论信息
    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }
    /**
     *
     * 将tags集合转换为tagIds字符串形式：“1,2,3”,用于编辑博客时显示博客的tag
      */
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            //把标签内容转为字符串 逗号隔开：
            StringBuilder sb = new StringBuilder();
            int i=0;
            for(;i<tags.size()-1;i++){
                sb.append(tags.get(i).getId());
                sb.append(",");
            }
            //i==tags.size()-1或者i>=tags.size()
            if(i==tags.size()-1){
                sb.append(tags.get(i).getId());
            }
            return sb.toString();
        }
        return tagIds;//默认为null
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", typeId=" + typeId +
                ", userId=" + userId +
                ", type=" + (type==null?"空":type.getName()) +
                ", user=" + (user==null?"yuyefanhua":user.getUsername()) +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
