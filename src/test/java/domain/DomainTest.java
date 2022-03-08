/**
 * @author 60417
 * @date 2022/2/18
 * @time 21:36
 * @todo
 */
package domain;

import com.yuyefanhua.blogsystem.config.MyBatisConfig;
import com.yuyefanhua.blogsystem.dao.*;
import com.yuyefanhua.blogsystem.domain.Blog;
import com.yuyefanhua.blogsystem.domain.Tag;
import com.yuyefanhua.blogsystem.domain.Type;
import com.yuyefanhua.blogsystem.domain.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration(classes = {MyBatisConfig.class})
//设置激活环境dev，使用mysql数据库，需要
@ActiveProfiles("product")
public class DomainTest {
    @Autowired
    private TypeDao typeDao;
    @Autowired
    private TagDao tagDao;
    @Test
    public void test_tag_type_Dao(){
        //查到多个，只返回一个：
        List<Type> typeList = typeDao.getTypeByName("未型");
        assertEquals(0,typeList.size());
//        Type t1 =typeList.get(0);
//        System.out.println(t1.getId());
    }
    //测试方法public
    @Test
    public void test_md(){
        String password = "123456";
        String s = DigestUtils.md5Hex(password);
        System.out.println(s);

    }
    @Autowired
    private JedisPool jedisPool;
    @Test
    public void test_redis(){
        assertNotNull(jedisPool);
        Jedis resource = jedisPool.getResource();
        resource.set("token","tesntsssss");
        String token = resource.get("token");
        assertEquals("tesntsssss",token);

    }
    @Test
    public void test_Dao(){
        int num = 50;
        num = num++*2;
        System.out.println(num);
        assertEquals(100,num);
        viewsDao.updateViewsForPeople();
        System.out.println(viewsDao.getAllViewsForPeople());
        System.out.println(viewsDao.getAMonthViewsForPeople());
        System.out.println(viewsDao.getYesterdayViewsForPeople());
        assertEquals(1,viewsDao.getAllViewsForPeople());

    }
    @Autowired
    private ViewsDao viewsDao;
    //设置一个数据源：

    @Autowired
    private BlogDao blogDao;
    @Test
    public void Test_Blog(){
            Blog blog = new Blog();
//            Tag tag1 = new Tag(1L,"test1",null);
//            Tag tag2 = new Tag(2L,"test2",null);
//            Tag tag3 = new Tag(3L,"test3",null);
//            ArrayList<Tag> tags = new ArrayList<>();
//            tags.add(tag1);tags.add(tag2);tags.add(tag3);
//
//            blog.setTags(tags);
//            blog.init();
//        String tagIds = blog.getTagIds();
//        System.out.println(tagIds);
//        assertEquals("1,2,3",tagIds);
//        blog.setTypeId(1L);//类型id为1
//        blog.setTitle("");//类型id为1
        blog.setTitle("实战");
        blog.setRecommend(true);
        //测试搜索功能：
        List<Blog> blogs = blogDao.searchAllBlog(blog);
        System.out.println(blogs);
        System.out.println("测试是否拉到type");
        //拉得到（需要别名匹配上)
        List<Blog> allBlog = blogDao.getAllBlog();
        System.out.println(allBlog);
        assertEquals(0,blogs.size());

    }
    @Test
    @Transactional
    public void Test_BlogDao(){
        Blog blog = blogDao.getBlog(1L);
        Blog detailedBlog = blogDao.getDetailedBlog(1l);
        System.out.println(detailedBlog.toString());
        System.out.println(blog);
        assertEquals((Object) 1L,blog.getTypeId());
        List<Blog> byYear = blogDao.findByYear("2022");
        assertEquals(4,byYear.size());
        List<String> groupYear = blogDao.findGroupYear();
        assertEquals(4,groupYear.size());
        assertEquals("2022",groupYear.get(0));
        assertEquals("2022",groupYear.get(1));
        assertEquals("2022",groupYear.get(2));
        assertEquals("2022",groupYear.get(3));
    }
    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    public void Test_UserDao(){
        String username = "yuyefanhua";
        String password = "123456";
        User user = userDao.queryByUsernameAndPassword(username, password);
        System.out.println(user.toString());
    }

}
