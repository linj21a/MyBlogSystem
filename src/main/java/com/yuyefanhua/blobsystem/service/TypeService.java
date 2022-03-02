/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:17
 * @todo
 */
package com.yuyefanhua.blobsystem.service;

import com.yuyefanhua.blobsystem.domain.Type;
import org.springframework.stereotype.Service;

import java.util.List;
public interface TypeService {
    int saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    List<Type> getAllType();

    List<Type> getBlogType();  //首页右侧展示type对应的博客数量

    int updateType(Type type);

    int deleteType(Long id);
}
