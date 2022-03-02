/**
 * @author 60417
 * @date 2022/2/19
 * @time 16:17
 * @todo
 */
package com.yuyefanhua.blobsystem.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
//@Configuration
//@Configuration
public class DataSourceConfig {
    /**
     * 嵌入式数据库的配置——在开发环境使用
     * 当且仅当 development profile 处于激活状态时，会创建嵌入式数据库
     * @return dataSource
     */
    @Profile("dev")
    @Bean
    public DataSource embeddedDataSource(){
        //可以使用建造者模式：
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:blogSystem/blob.sql")
                .build();
    }
    /**
     * 测试环境的数据源 dbcp
     * 当且仅当 qa profile 处于激活状态时，会创建 DBCP BasicDataSource
     * @return BasicDataSource
     */
    @Profile("qa")
    @Bean
    public DataSource data(){
        BasicDataSource dbcp = new BasicDataSource();
        dbcp.setDriverClassName("com.mysql.cj.jdbc.Driver");//设置驱动的类路径
        dbcp.setUrl("jdbc:mysql://localhost:3306/blogsystem?" +
                "autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8");
        dbcp.setUsername("root");
        dbcp.setPassword("lh123321root");
        //其他参数
        dbcp.setInitialSize(5);
        dbcp.setMaxIdle(10);//最大同时处理的连接数
        return dbcp;
    }
}
