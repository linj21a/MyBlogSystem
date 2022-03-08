/**
 * @author 60417
 * @date 2022/2/18
 * @time 22:09
 * @todo
 */
package com.yuyefanhua.blogsystem.dao;

import com.yuyefanhua.blogsystem.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    //多个参数时，mybatsi需要使用@param来进行参数绑定
    User queryByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    boolean updateUser(User user);

    /**
     * 注册用户
     * @param user
     */
    void register(User user);
}
