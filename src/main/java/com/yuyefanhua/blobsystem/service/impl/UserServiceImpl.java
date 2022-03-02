/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:32
 * @todo
 */
package com.yuyefanhua.blobsystem.service.impl;

import com.yuyefanhua.blobsystem.dao.UserDao;
import com.yuyefanhua.blobsystem.domain.User;
import com.yuyefanhua.blobsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User checkUser(String username, String password) {
        return userDao.queryByUsernameAndPassword(username,password) ;
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}
