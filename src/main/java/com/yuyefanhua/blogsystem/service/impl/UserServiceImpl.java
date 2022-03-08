/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:32
 * @todo
 */
package com.yuyefanhua.blogsystem.service.impl;

import com.yuyefanhua.blogsystem.dao.UserDao;
import com.yuyefanhua.blogsystem.domain.User;
import com.yuyefanhua.blogsystem.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User checkUser(String username, String password) {
//        password需要加密存储到数据库里面
        password = DigestUtils.md5Hex(password);//加密
        return userDao.queryByUsernameAndPassword(username,password) ;
    }

    @Override
    public void updateUser(User user) {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        userDao.updateUser(user);
    }
    @Override
    public void register(User user){
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        userDao.register(user);
    }
}
