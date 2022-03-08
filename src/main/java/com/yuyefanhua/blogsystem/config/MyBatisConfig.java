/**
 * @author 60417
 * @date 2022/2/18
 * @time 22:43
 * @todo
 */
package com.yuyefanhua.blogsystem.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 配置mybatis 配置
 */
@Configuration
@ImportResource({"classpath:blogSystem/applicationContext.xml"})
//@MapperScan(value = {"classpath:blogSystem/mapper/*.xml"}) //这个是
public class MyBatisConfig {
    /**
     * 需要一个salsessionfactory   和一个dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
//        设置xml映射配置文件 报错：不合法调用 No ServletContext set
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:blogSystem/mapper/*.xml");
        factoryBean.setMapperLocations(resources);
//        设置mapper里面的daoxml配置文件的别名
        factoryBean.setTypeAliasesPackage("com.yuyefanhua.blogsystem.domain");
        return factoryBean.getObject();
    }
    //自动注入一个数据源
    @Autowired
    private DataSource dataSource;
}
