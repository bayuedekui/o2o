
/*新建tb_area表(用于存放区域相关信息)*/
drop table if exists `tb_area`;
create table `tb_area`(
	`area_id` int(5) NOT NULL AUTO_INCREMENT,
	`area_name` varchar(200) NOT NULL,
	`area_desc` varchar(1000) DEFAULT NULL,
	`priority` int(2) NOT NULL DEFAULT '0',
	`create_time` datetime DEFAULT NULL,
	`last_edit_time` datetime DEFAULT NULL,
	PRIMARY KEY (`area_id`),
	UNIQUE KEY `UK_AREA` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

insert into `tb_area` values(1,'南园一舍','宿舍区','2',null,null);
update `tb_area` set area_name='测试地址' where area_id='1';
/*创建头条信息表*/
drop table if exists `tb_head_line`;
create table `tb_head_line`(
	`line_id` int(100) not null auto_increment,
	`line_name` varchar(1000) default null,
	`line_link` varchar(2000) default null,
	`line_img` varchar(2000) default null,
	`priority` int(2) default null,
	`enable_status` int(2) not null default '0',
	`create_time` datetime default null,
	`last_edit_time` datetime default null,
	primary key (`line_id`)
)engine=InnoDB auto_increment=16 default charset=utf8;

insert into `tb_head_line` values(1,'测试head_line',null,null,4,1,null,null);
/*创建本地用户表*/
drop table if exists `tb_local_auth`;
create table `tb_local_auth`(
	`local_auth_id` int(10) not null auto_increment,
	`user_id` int(10) default null,
	`user_name` varchar(128) collate utf8_unicode_ci not null,
	`password` varchar(128) collate utf8_unicode_ci not null,
	`create_time` datetime default null,
	`last_edit_time` datetime default null,
	primary key (`local_auth_id`),
	unique key `uk_local_profile` (`user_name`),
	key `fk_local_profile` (`user_id`),
	constraint `fk_local_profile` foreign key (`user_id`) references `tb_person_info` (`user_id`)
)engine=InnoDB auto_increment=8 default charset=utf8 collate=utf8_unicode_ci;

/*创建微信用户表tb_wechat_auth*/
drop table if exists `tb_wechat_auth`;
create table `tb_wechat_auth`(
	 `wechat_auth_id` int(10) not null auto_increment,
	 `user_id` int(10) not null,
	 `open_id` varchar(512) collate utf8_unicode_ci,
	 `create_time` datetime default null,
	 `last_edit_time` datetime default null,
	 primary key (`wechat_auth_id`),
	 key `fk_oauth_profile` (`user_id`),
	 key `uk_oauth` (`open_id`(255)),
	 constraint `fk_oauth_profile` foreign key (`user_id`) references `tb_persion_info` (`user_id`)
)engine=InnoDB auto_increment=8 default charset=utf8 collate=utf8_unicode_ci;





/*创建个人信息表*/
drop table if exists `tb_persion_info`;
create table `tb_persion_info`(
	`user_id` int(10) not null auto_increment,
	`name` varchar(32) collate utf8_unicode_ci,
	`birthday` datetime default null,
	`gender` varchar(2) collate utf8_unicode_ci default null,
	`phone` varchar(32) collate utf8_unicode_ci default null,
	`email` varchar(128) collate utf8_unicode_ci default null,
	`profile_image` varchar(1024) collate utf8_unicode_ci default null,
	`custom_flag` varchar(2) not null default '0',
	`show_owner_flag` int(2) not null default '0',
	`admin_flag` int(2) not null,
	`create_time` datetime default null,
	`last_eidt_time` datetime default null,
	`enable_status` int(2) not null default '0',
	 primary key (`user_id`)
)engine=InnoDB auto_increment=12 default charset=utf8 collate=utf8_unicode_ci;
 
insert into `tb_person_info` values(1,'测试persioninfo',null,null,15895870454,null,null,0,0,0,null,null,0); 
 /*创建tb_product_category表*/
 drop table if exists `tb_product_category`;
 create table `tb_product_category`(
	`product_category_id` int(11) not null auto_increment,
	`shop_id` int(20) not null default '0',
	`product_category_name` varchar(100) not null,
	`product_category_desc` varchar(500) default null,
	`priority` int(2) default '0',
	`create_time` datetime default null,
	`last_edit_time` datetime default null,
	primary key (`product_category_id`),
	key `fk_procate_shop` (`shop_id`),
	constraint `fk_procate_shop` foreign key(`shop_id`) references `tb_shop` (`shop_id`)
	)engine=InnoDB auto_increment=16 default charset=utf8;
 insert into tb_product_category values(1,1,'测试product_category',null,0,null,null);

 
 
 /*创建tb_shop_category表*/
 drop table if exists `tb_shop_category`;
 create table `tb_shop_category`(
	`shop_category_id` int(11) not null auto_increment,
	`shop_category_name` varchar(100) not null default '',
	`shop_category_desc` varchar(1000) default '',
	`shop_category_img`  varchar(2000) default '',
	`priority` int(2) not null default '0',
	`create_time` datetime default null,
	`last_edit_time` datetime default null,
	`parent_id` int(11) default null,
	primary key (`shop_category_id`),
	key `fk_shop_category_self` (`parent_id`),
	constraint `fk_shop_category_self` foreign key (`parent_id`) references `tb_shop_category` (`shop_category_id`)
 )engine=InnoDB auto_increment=33 default charset=utf8;
 insert into tb_shop_category values(1,'测试shop_category','','',0,null,null,null);
 
 /*创建tb_shop表*/
drop table if exists `tb_shop`;
create table `tb_shop`(
	`shop_id` int(10) not null auto_increment,
	`owner_id` int(10) not null comment '店铺创始人',
	`area_id` int(5) default null,
	`shop_category_id` int(11) default null,
	`parent_category_id` int(11) default null,
	`shop_name` varchar(256) collate utf8_unicode_ci not null,
	`shop_desc` varchar(256) collate utf8_unicode_ci default null,
	`shop_addr` varchar(256) collate utf8_unicode_ci default null,
	`phone` varchar(128) collate utf8_unicode_ci default null,
	`shop_img` varchar(1024) collate utf8_unicode_ci default null,
	`longitude` double(16,12) default null,
	`latitude` double(16,12) default null,
	`priority` int(3) default '0',
	`create_time` datetime default null,
	`last_edit_time` datetime default null,
	`enable_status` int(2) not null default '0',
	`advice` varchar(255) collate utf8_unicode_ci default null,
	primary key (`shop_id`),
	KEY `fk_shop_profile` (`owner_id`),
	KEY `fk_shop_area` (`area_id`),
	KEY `fk_shop_shopcate` (`shop_category_id`),
	KEY `fk_shop_parentcate` (`parent_category_id`),
	CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) references `tb_area` (`area_id`),
	CONSTRAINT `fk_shop_parentcate` FOREIGN KEY (`parent_category_id`) references `tb_shop_category` (`shop_category_id`),
	CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) references `tb_person_info` (`user_id`),
	CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) references `tb_shop_category` (`shop_category_id`)
 )ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT charset=utf8 collate=utf8_unicode_ci; 
 
insert into tb_shop values(1,1,1,1,1,'测试shop_name',null,null,null,null,null,null,0,null,null,0,null);
 
/*要在表tb_product_category表和shop表建好了才能创建product表*/
drop table if exists `tb_product`;
create table `tb_product`(
	`product_id` int(100) not null auto_increment,
	`product_name` varchar(100) not null,
	`product_desc` varchar(2000) default null,
	`img_addr` varchar(2000) default '',
	`normal_price` varchar(100) default null,
	`promotion_price` varchar(100) default null,
	`priority` int(2) not null default '0',
	`create_time` datetime default null,
	`last_edit_time` datetime default null,
	`enable_status` int(2) not null default '0',
	`point` int(10) default null,
	`product_category_id` int (11) default null,
	`shop_id` int(20) not null default '0',
	primary key (`product_id`),
	key `fk_product_shop` (`shop_id`),
	key `fk_product_procate` (`product_category_id`),
	constraint `fk_product_procate` foreign key (`product_category_id`) references `tb_product_category` (`product_category_id`),
	constraint `fk_product_shop` foreign key (`shop_id`) references `tb_shop` (`shop_id`)
)engine=InnoDB auto_increment=16 default charset=utf8; 
insert into tb_product values(1,'测试tb_product',null,'',null,null,0,null,null,0,null,1,1);

/*创建tb_product_img表*/
drop table if exists `tb_product_img`;
create table `tb_product_img`(
	`product_img_id` int(20) not null auto_increment,
	`img_addr` varchar(2000) not null,
	`img_desc` varchar(2000) default null,
	`priority` int(2) default '0',
	`create_time` datetime default null,
	`last_edit_time` datetime default null,
	`product_id` int(20) default null,
	primary key (`product_img_id`),
	key `fk_proming_product` (`product_id`),
	constraint `fk_proming_product` foreign key (`product_id`) references `tb_product` (`product_id`) on delete cascade on update cascade
)engine=InnoDB auto_increment=38 default charset=utf8;
insert into tb_product_img values(1,'测试tb_product_img',null,0,null,null,1);

/*创建tb_shop_auth_map表*/
drop table if exists `tb_shop_auth_map`;
create table `tb_shop_auth_map`(
	`shop_auth_id` int(10) not null auto_increment,
	`employee_id` int(10) not null,
	`shop_id` int(10) not null,
	`name` varchar(255) collate utf8_unicode_ci default null comment '冗余是为了让shop在查找员工的时候,不需要去连tb_shop表,直接去tb_shop_auth_map就okay',
	`title` varchar(255) collate utf8_unicode_ci default null,
	`title_flag` int(2) default null,
	`enable_status` int(10) not null default '0',
	`create_time` datetime default null,
	`last_edit_time` datetime default null,
	primary key (`shop_auth_id`),
	key `fk_shop_auth_map_shop` (`shop_id`),
	key `uk_shop_auth_map` (`employee_id`,`shop_id`),
	constraint `fk_shop_auth_map_employee` foreign key (`employee_id`) references `tb_person_info` (`user_id`),
	constraint `fk_shop_auth_map_shop` foreign key (`shop_id`) references `tb_shop` (`shop_id`) 
	
)engine=InnoDB auto_increment=27 default charset=utf8 collate=utf8_unicode_ci;
























