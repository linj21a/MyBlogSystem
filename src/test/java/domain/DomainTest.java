/**
 * @author 60417
 * @date 2022/2/18
 * @time 21:36
 * @todo
 */
package domain;

import com.yuyefanhua.blobsystem.config.BlobAppInitializer;
import com.yuyefanhua.blobsystem.config.DataSourceConfig;
import com.yuyefanhua.blobsystem.config.MyBatisConfig;
import com.yuyefanhua.blobsystem.dao.BlogDao;
import com.yuyefanhua.blobsystem.dao.UserDao;
import com.yuyefanhua.blobsystem.domain.Blog;
import com.yuyefanhua.blobsystem.domain.Tag;
import com.yuyefanhua.blobsystem.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration(classes = {MyBatisConfig.class})
//设置激活环境dev，使用mysql数据库，需要
@ActiveProfiles("product")
public class DomainTest {
    @Test
    public void test(){
        int num = 50;
        num = num++*2;
        System.out.println(num);
        assertEquals(100,num);
    }
    //设置一个数据源：

    @Autowired
    private BlogDao blogDao;
    @Test
    public void Test_Blog(){
            Blog blog = new Blog();
            Tag tag1 = new Tag(1L,"test1",null);
            Tag tag2 = new Tag(2L,"test2",null);
            Tag tag3 = new Tag(3L,"test3",null);
            ArrayList<Tag> tags = new ArrayList<>();
            tags.add(tag1);tags.add(tag2);tags.add(tag3);

            blog.setTags(tags);
            blog.init();
        String tagIds = blog.getTagIds();
        System.out.println(tagIds);
        assertEquals("1,2,3",tagIds);

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
