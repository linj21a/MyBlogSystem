CREATE DATABASE if not exists  BlogSystem ;
USE BlogSystem;

-- 博客表t_blog
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog` (
      `id` bigint NOT NULL AUTO_INCREMENT ,
      `title` varchar(255) DEFAULT NULL,
      `content` text ,
      `first_picture` varchar(255) DEFAULT NULL,
      `flag` varchar(255) DEFAULT NULL,
      `views` int DEFAULT NULL,
      `appreciation` boolean NOT NULL DEFAULT true,
      `share_statement` boolean NOT NULL DEFAULT false,
      `commentabled` boolean NOT NULL DEFAULT true,
      `published` boolean NOT NULL DEFAULT true,
      `recommend` boolean NOT NULL DEFAULT false,
      `create_time` datetime DEFAULT NULL,
      `update_time` datetime DEFAULT NULL,
      `type_id` bigint DEFAULT NULL,
      `user_id` bigint DEFAULT NULL,
      `description` text,
      `tag_ids` varchar(100) DEFAULT NULL,
      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=UTF8MB4 ROW_FORMAT=COMPACT;

/*伪造数据 t_blog */

insert  into `t_blog`(`id`,`title`,`content`,
                      `first_picture`,`flag`,`views`,`appreciation`,`share_statement`,
                      `commentabled`,`published`,`recommend`,`create_time`,`update_time`,
                      `type_id`,`user_id`,`description`,`tag_ids`)
                      values (1,'实战1','这是一个测试正文的内容1',
                              'https://pic4.zhimg.com/v2-0f922e2f43da24e4deb943938b056881_b.jpg','原创',3,true,false,false,false,false,
                              '2022-02-02 10:11:21','2022-02-12 11:07:43',1,1,'简单测试','1'),
                             (2,'实战2','这是一个测试正文的内容2',
                              'https://pic4.zhimg.com/v2-0f922e2f43da24e4deb943938b056881_b.jpg','转载',21,true,false,true,false,true,
                              '2022-01-02 10:11:21','2022-01-12 11:07:43',2,1,'简单测试2','5'),
                             (3,'实战3','这是一个测试正文的内容3',
                              'https://pic4.zhimg.com/v2-0f922e2f43da24e4deb943938b056881_b.jpg','原创',31,true,false,true,false,true,
                              '2022-02-02 10:11:21','2022-02-12 11:07:43',2,1,'简单测试','10'),
                             (4,'实战4','这是一个测试正文的内容4',
                              'https://pic4.zhimg.com/v2-0f922e2f43da24e4deb943938b056881_b.jpg','转载',45,true,false,true,false,true,
                              '2022-01-02 10:11:21','2022-01-12 11:07:43',1,1,'简单测试2','2');

/*博客标签库 存放已经生成的博客信息和对于的标签信息id */

DROP TABLE IF EXISTS `t_tags`;

CREATE TABLE `t_tags` (
   `id` int NOT NULL AUTO_INCREMENT,
   `tag_id` bigint DEFAULT NULL,
   `blog_id` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=UTF8MB4 ROW_FORMAT=COMPACT;

/*伪造数据 t_tags */

insert  into `t_tags`(`id`,`tag_id`,`blog_id`) values
        (1,1,'1'),
        (2,5,'2'),
        (3,10,'3'),
        (4,2,'4');
/*   标签库信息 t_tag */

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=UTF8MB4 ROW_FORMAT=COMPACT;

/*  t_tag */

insert  into `t_tag`(`id`,`name`) values
                                      (1,'杂谈'),
                                      (5,'大数据'),
                                      (10,'Java'),
                                      (2,'Hadoop'),
                                      (3,'Spark'),
                                      (4,'面试');


/* 评论 */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `nickname` varchar(255) DEFAULT NULL,
     `email` varchar(255) DEFAULT NULL,
     `content` varchar(255) DEFAULT NULL,
     `avatar` varchar(255) DEFAULT NULL,
     `create_time` datetime DEFAULT NULL,
     `blog_id` bigint DEFAULT NULL,
     `parent_comment_id` bigint DEFAULT NULL,
     `admincomment` int DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=UTF8MB4 ROW_FORMAT=COMPACT;

/* 伪造数据评论 t_comment */

insert  into `t_comment`
(`id`,`nickname`,`email`,`content`,`avatar`,`create_time`,`blog_id`,`parent_comment_id`,`admincomment`) values
(1,'小白','bai@qq.com','小白的评论','/images/avatar.png','2020-03-15 21:28:13',4,-1,NULL),
(2,'小红','hong@qq.com','小红的评论','/images/avatar.png','2020-03-15 21:35:02',4,-1,NULL),
(5,'小蓝','lan@qq.com','小蓝的评论','/images/avatar.png','2020-03-16 15:04:24',4,-1,NULL),
(7,'波波','691639910@qq.com','博主的评论','https://profile.csdnimg.cn/1/7/E/1_qq_44861675','2020-03-16 15:11:07',2,-1,1),
(8,'牛儿','2333@qq.com','永远保持牛逼','/images/avatar.png','2020-03-24 17:41:17',11,-1,0);

/* 博客的类型——用于归档  t_type */

DROP TABLE IF EXISTS `t_type`;

CREATE TABLE `t_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=UTF8MB4 ROW_FORMAT=COMPACT;

/*Data for the table `t_type` 这里的id会在blob的type_id里面 */

insert  into `t_type`(`id`,`name`) values
                                       (1,'测试1'),
                                       (2,'动漫'),
                                       (3,'思维');

/*用户表单 t_user */

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint  NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `email` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=UTF8MB4 ROW_FORMAT=COMPACT;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`nickname`,`username`,`password`,`email`,`avatar`,`type`,`create_time`,`update_time`)
values(1,'yuyefanhua','yuyefanhua','e10adc3949ba59abbe56e057f20f883e','123456.qq.com','/static/images/avatar.png',1,'2022-02-02 10:21:23',NULL);

/* 网站访问量的记录*/

DROP TABLE IF EXISTS `t_views`;
create table t_views(
    dates datetime default NULL,
    views int default 0
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8MB4 ROW_FORMAT=COMPACT;