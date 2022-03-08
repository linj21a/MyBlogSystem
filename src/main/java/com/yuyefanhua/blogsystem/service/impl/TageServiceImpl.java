/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:28
 * @todo
 */
package com.yuyefanhua.blogsystem.service.impl;

import com.yuyefanhua.blogsystem.dao.TagDao;
import com.yuyefanhua.blogsystem.domain.Tag;
import com.yuyefanhua.blogsystem.service.TageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.thymeleaf.util.ObjectUtils.convertToList;

@Service
public class TageServiceImpl implements TageService {
    @Autowired
    private TagDao tagDao;

    @Override
    public int saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagDao.getTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Override
    public List<Tag> getAllTag() {
        return tagDao.getAllTag();
    }

    @Override
    public List<Tag> getBlogTag() {
        return tagDao.getBlogTag();
    }
    @Override
    public List<Tag> getTagByString(String tagIds) {
        List<Tag> tags = new ArrayList<>();
        String[] split = tagIds.split(",");
        for(int i=0;i<split.length;i++){
            //tagIds
            tags.add(tagDao.getTag(Long.valueOf(split[i])));
        }
        return tags;
    }

    @Override
    public long size() {
        return tagDao.size();
    }

    @Override
    public long getMaxId() {
        return tagDao.getMaxId();
    }

    @Override
    public int updateTag(Tag tag) {
        return tagDao.updateTag(tag);
    }

    @Override
    public int deleteTag(Long id) {
        return tagDao.deleteTag(id);
    }
}
