/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:28
 * @todo
 */
package com.yuyefanhua.blobsystem.service.impl;

import com.yuyefanhua.blobsystem.domain.Tag;
import com.yuyefanhua.blobsystem.service.TageService;
import com.yuyefanhua.blobsystem.service.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TageServiceImpl implements TageService {
    @Override
    public int saveTag(Tag tag) {
        return 0;
    }

    @Override
    public Tag getTag(Long id) {
        return null;
    }

    @Override
    public Tag getTagByName(String name) {
        return null;
    }

    @Override
    public List<Tag> getAllTag() {
        return null;
    }

    @Override
    public List<Tag> getBlogTag() {
        return null;
    }

    @Override
    public int updateTag(Tag tag) {
        return 0;
    }

    @Override
    public int deleteTag(Long id) {
        return 0;
    }
}
