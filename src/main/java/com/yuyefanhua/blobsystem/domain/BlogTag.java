/**
 * @author 60417
 * @date 2022/2/18
 * @time 21:49
 * @todo
 */
package com.yuyefanhua.blobsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 映射 博客id和标签的关系
 * t_tags
 *
 */
@Data
@NoArgsConstructor
public class BlogTag {
    private int id;//条码id，无用，自动增长
    private Long tagId;//标签id
    private Long blogId;//博客id

    public BlogTag(Long tagId, Long blogId) {
        this.tagId = tagId;
        this.blogId = blogId;
    }

}
