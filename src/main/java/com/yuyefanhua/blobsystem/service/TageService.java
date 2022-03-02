/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:28
 * @todo
 */
package com.yuyefanhua.blobsystem.service;

import com.yuyefanhua.blobsystem.domain.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
public interface TageService {
    int saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);//从字符集中获取tag的集合

    List<Tag> getAllTag();

    List<Tag> getBlogTag();  //首页展示博客标签

    int updateTag(Tag tag);

    int deleteTag(Long id);
}
