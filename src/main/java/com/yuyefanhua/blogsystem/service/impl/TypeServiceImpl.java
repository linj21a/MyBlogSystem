/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:18
 * @todo
 */
package com.yuyefanhua.blogsystem.service.impl;

import com.yuyefanhua.blogsystem.dao.TypeDao;
import com.yuyefanhua.blogsystem.domain.Type;
import com.yuyefanhua.blogsystem.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeDao typeDao;//底层仓库
    @Override
    public int saveType(Type type) {//新增
        return typeDao.saveType(type);
    }

    @Override
    public Type getType(Long id) {//获取
        return typeDao.getType(id);
    }

    @Override
    public Type getTypeByName(String name) {//查找name的type
        List<Type> typeByName = typeDao.getTypeByName(name);
        if(typeByName==null||typeByName.size()==0){
            return null;
        }
        return typeByName.get(0);
    }

    @Override
    public int count() {
        return typeDao.count();
    }

    @Override
    public List<Type> getAllType() {//获取所有type
        return typeDao.getAllType();
    }

    @Override
    public List<Type> getBlogType() {//获取
        return typeDao.getBlogType();
    }

    @Override
    public int updateType(Type type) {
        return typeDao.updateType(type);
    }

    @Override
    public int deleteType(Long id) {
        return typeDao.deleteType(id);
    }

    @Override
    public long maxTypeId() {
        return typeDao.maxTypeId();
    }
}
