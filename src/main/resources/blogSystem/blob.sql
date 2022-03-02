CREATE DATABASE if not exists  BlogSystem ;
USE BlogSystem;

-- 博客表t_blog
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT ,
      `title` varchar(255) DEFAULT NULL,
      `content` text ,
      `first_picture` varchar(255) DEFAULT NULL,
      `flag` varchar(255) DEFAULT NULL,
      `views` int(11) DEFAULT NULL,
      `appreciation` int(11) NOT NULL DEFAULT '0',
      `share_statement` int(11) NOT NULL DEFAULT '0',
      `commentabled` int(11) NOT NULL DEFAULT '0',
      `published` int(11) NOT NULL DEFAULT '0',
      `recommend` int(11) NOT NULL DEFAULT '0',
      `create_time` datetime DEFAULT NULL,
      `update_time` datetime DEFAULT NULL,
      `type_id` bigint(20) DEFAULT NULL,
      `user_id` bigint(20) DEFAULT NULL,
      `description` text,
      `tag_ids` varchar(100) DEFAULT NULL,
      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*伪造数据 t_blog */

insert  into `t_blog`(`id`,`title`,`content`,
                      `first_picture`,`flag`,`views`,`appreciation`,`share_statement`,
                      `commentabled`,`published`,`recommend`,`create_time`,`update_time`,
                      `type_id`,`user_id`,`description`,`tag_ids`)
                      values (1,'实战1','这是一个测试正文的内容1',
                              'https://pic4.zhimg.com/v2-0f922e2f43da24e4deb943938b056881_b.jpg','原创',3,1,1,1,1,1,
                              '2022-02-02 10:11:21','2022-02-12 11:07:43',1,1,'简单测试','1'),
                             (2,'实战2','这是一个测试正文的内容2',
                              'https://pic4.zhimg.com/v2-0f922e2f43da24e4deb943938b056881_b.jpg','转载',21,21,2,2,1,1,
                              '2022-01-02 10:11:21','2022-01-12 11:07:43',2,1,'简单测试2','5'),
                             (3,'实战3','这是一个测试正文的内容3',
                              'https://pic4.zhimg.com/v2-0f922e2f43da24e4deb943938b056881_b.jpg','原创',31,32,12,1,1,1,
                              '2022-02-02 10:11:21','2022-02-12 11:07:43',2,1,'简单测试','10'),
                             (4,'实战4','这是一个测试正文的内容4',
                              'https://pic4.zhimg.com/v2-0f922e2f43da24e4deb943938b056881_b.jpg','转载',45,22,23,2,1,1,
                              '2022-01-02 10:11:21','2022-01-12 11:07:43',1,1,'简单测试2','2');

/*博客标签库 存放已经生成的博客信息和对于的标签信息id */

DROP TABLE IF EXISTS `t_tags`;

CREATE TABLE `t_tags` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `tag_id` bigint(20) DEFAULT NULL,
   `blog_id` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*伪造数据 t_tags */

insert  into `t_tags`(`id`,`tag_id`,`blog_id`) values
        (1,1,'1'),
        (2,5,'2'),
        (3,10,'3'),
        (4,2,'4');
/*   标签库信息 t_tag */

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

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
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `nickname` varchar(255) DEFAULT NULL,
     `email` varchar(255) DEFAULT NULL,
     `content` varchar(255) DEFAULT NULL,
     `avatar` varchar(255) DEFAULT NULL,
     `create_time` datetime DEFAULT NULL,
     `blog_id` bigint(20) DEFAULT NULL,
     `parent_comment_id` bigint(20) DEFAULT NULL,
     `admincomment` int(11) DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/* 伪造数据评论 t_comment */

insert  into `t_comment`
(`id`,`nickname`,`email`,`content`,`avatar`,`create_time`,`blog_id`,`parent_comment_id`,`admincomment`) values
(1,'小白','bai@qq.com','小白的评论','/images/avatar.jpg','2020-03-15 21:28:13',4,-1,NULL),
(2,'小红','hong@qq.com','小红的评论','/images/avatar.jpg','2020-03-15 21:35:02',4,-1,NULL),
(5,'小蓝','lan@qq.com','小蓝的评论','/images/avatar.jpg','2020-03-16 15:04:24',4,-1,NULL),
(7,'波波','691639910@qq.com','博主的评论','https://profile.csdnimg.cn/1/7/E/1_qq_44861675','2020-03-16 15:11:07',2,-1,1),
(8,'牛儿','2333@qq.com','永远保持牛逼','/images/avatar.jpg','2020-03-24 17:41:17',11,-1,0);

/* 博客的类型——用于归档  t_type */

DROP TABLE IF EXISTS `t_type`;

CREATE TABLE `t_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `t_type` 这里的id会在blob的type_id里面 */

insert  into `t_type`(`id`,`name`) values
                                       (1,'测试1'),
                                       (2,'动漫'),
                                       (3,'思维');

/*用户表单 t_user */

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `email` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `type` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`nickname`,`username`,`password`,`email`,`avatar`,`type`,`create_time`,`update_time`)
values(1,'yuyefanhua','yuyefanhua','123456','123456.qq.com','/image/me.png',1,'2022-02-02 10:21:23',NULL);

/* 网站访问量的记录*/

DROP TABLE IF EXISTS `t_views`;
create table t_views(
    dates datetime default NULL,
    views int(11) default 0
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;