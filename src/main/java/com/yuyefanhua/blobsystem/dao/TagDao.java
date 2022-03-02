/**
 * @author 60417
 * @date 2022/2/18
 * @time 22:09
 * @todo
 */
package com.yuyefanhua.blobsystem.dao;

import com.yuyefanhua.blobsystem.domain.Tag;

import java.util.List;

public interface TagDao {
    int saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    List<Tag> getAllTag();

    List<Tag> getBlogTag();  //首页展示博客标签

    int updateTag(Tag tag);

    int deleteTag(Long id);
}
