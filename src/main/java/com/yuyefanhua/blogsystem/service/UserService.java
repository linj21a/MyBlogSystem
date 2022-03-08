/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:31
 * @todo
 */
package com.yuyefanhua.blogsystem.service;

import com.yuyefanhua.blogsystem.domain.User;

public interface UserService {
    User checkUser(String username,String password);
    void updateUser(User user);
    void register(User user);
}
