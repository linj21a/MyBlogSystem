/**
 * @author 60417
 * @date 2022/2/18
 * @time 22:09
 * @todo
 */
package com.yuyefanhua.blobsystem.dao;

import com.yuyefanhua.blobsystem.domain.Type;

import java.util.List;

public interface TypeDao {
    int saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    List<Type> getAllType();

    List<Type> getBlogType();  //首页右侧展示type对应的博客数量

    int updateType(Type type);

    int deleteType(Long id);
}
