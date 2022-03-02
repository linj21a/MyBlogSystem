/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:31
 * @todo
 */
package com.yuyefanhua.blobsystem.service;

import com.yuyefanhua.blobsystem.domain.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User checkUser(String username,String password);
    void updateUser(User user);
}
