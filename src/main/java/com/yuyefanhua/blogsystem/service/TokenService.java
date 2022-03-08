/**
 * @author 60417
 * @date 2022/3/3
 * @time 16:44
 * @todo
 */
package com.yuyefanhua.blogsystem.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yuyefanhua.blogsystem.domain.User;
import com.yuyefanhua.blogsystem.util.RedisUtils;
import nl.bitwalker.useragentutils.UserAgent;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 负责管理token
 * 生成、保存、校验、删除
 */
@Service("tokenService")
public class TokenService {
    @Autowired
    private RedisUtils redisUtil;//配置文件注入
    private final int expire = 2*60*60;//默认token过期时间

    public TokenService(){
    }
    //生成token(格式为token:设备-加密的用户名-时间-六位随机数)
    public String generateToken(String userAgent, User user){
        StringBuilder token = new StringBuilder();
        //加token:
        token.append("token:");
        //加设备
        UserAgent userAgent1 = UserAgent.parseUserAgentString(userAgent);
        if (userAgent1.getOperatingSystem().isMobileDevice()){
            token.append("MOBILE-");
        } else {
            token.append("PC-");
        }
        //加加密的用户名  DigestUtils
        token.append(DigestUtils.md5Hex(user.getUsername())).append("-");
        //加时间
        token.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).append("-");
        //加六位随机数111111-999999
        token.append(new Random().nextInt((999999 - 111111 + 1)) + 111111);
        token.append("-userId").append(user.getId());//用户id
        System.out.println("生成token==>" + token);
        return token.toString();
    }

    //保存token
    public void saveToken(String token, User user){
        user.setPassword("?");//密码保护
        //如果是PC，那么token保存两个小时；如果是MOBILE
        System.out.println("保存token");
        //加加密的用户名  DigestUtils
        String username_md = DigestUtils.md5Hex(user.getUsername());
        if (token.startsWith("token:PC")) {
            //设置过期时间
//            USER需要
            redisUtil.setex("userToken-"+user.getId(),expire,
                    token);//转换的json忽略空指针
            redisUtil.setex(username_md,expire,JSONObject.toJSONString(user,SerializerFeature.PrettyFormat));
        } else {
            redisUtil.set("userToken-"+user.getId(), token);
            redisUtil.set(username_md,JSONObject.toJSONString(user,SerializerFeature.PrettyFormat));
        }
    }
    public void delToken(User user){
        //加加密的用户名  DigestUtils
        String username_md = DigestUtils.md5Hex(user.getUsername());
        String userid = "userToken-"+user.getId();
        redisUtil.del(userid);
        redisUtil.del(username_md);
    }

    public  boolean check(String token,HttpSession session) {
        if(token==null||"".equals(token.trim()) || !token.startsWith("token:")){
            return false;
        }
        //token:PC-af4207d4bafad497088067cbdac5fbf9-20220304145820029-603615-userId123455666
        //token:MOBILE-af4207d4bafad497088067cbdac5fbf9-20220304145820029-603615-userId123455666
        //获取userid
        String[] split = token.split("-");
        if(split.length<5){
            return false;
        }
        String username = split[1];
        String userid = "userToken-"+split[4].substring(6);
        System.out.println("userid:"+userid);
        System.out.println("username_md:"+username);
        //判断redis里面有没有：
        if( redisUtil.exists(username) && redisUtil.exists(userid)){
            //取出token，判断是否过期
            Long ttl = redisUtil.ttl(userid);
            if(ttl<0){
                return false;//过期或者不存在需要重新登录
            }else{//未过期，续期，无需重新登录
                String user_string = redisUtil.get(username);//user对象的序列化属性
                User user = JSONObject.parseObject(user_string,User.class);
                redisUtil.setex(username,expire,user_string);
                redisUtil.setex(userid,expire,token);
                session.setAttribute("user",user);//session里面添加user对象
                return true;
            }
        }
        return false;

    }
}