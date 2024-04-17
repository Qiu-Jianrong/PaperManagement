create database Paper_Management;
use Paper_Management;
-- 用户表
create table if not exists user (
	id int unsigned primary key auto_increment comment 'ID',
	username varchar(20) not null unique comment '用户名',
	password varchar(32)  comment '密码',
	nickname varchar(20)  default '' comment '昵称',
	email varchar(128) default '' comment '邮箱',
	user_pic varchar(128) default '' comment '头像',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '修改时间'
) comment '用户表';

-- 文献库
create table if not exists category(
	id int unsigned primary key auto_increment comment 'ID',
	category_name varchar(32) not null comment '分类名称',
	category_alias varchar(32) not null comment '分类别名',
	create_user int unsigned not null comment '创建人ID',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '修改时间',
    category_public tinyint(1) not null comment '文献库是否公开允许评论',
	constraint fk_category_user foreign key (create_user) references user(id) -- 外键约束
);

-- 论文表
create table if not exists article(
	id int unsigned primary key auto_increment comment 'ID',
    title varchar(30) not null comment '文章标题',
    abstract varchar(2000) not null comment '论文摘要',
    content mediumtext not null comment '文章正文, varchar不够长',
    state varchar(3) default '草稿' comment '文章状态: 只能是[已发布] 或者 [草稿]',
    score_amount int unsigned default 0 comment '参与评分的人数, 用以计算平均分',
    score numeric(5, 2) comment '文章平均得分，两位小数百分制',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '修改时间'
);

-- 论文-作者表,因为作者可能有多个，故不应放在article中
create table if not exists article_author(
    article_id int unsigned,
    author_id int unsigned,
    is_leader bool comment "是否是第一作者",
    is_corresponding bool comment "是否是通讯作者",
    constraint fk_articleauthor_atricle foreign key (article_id) references article(id),
    constraint fk_articleauthor_author foreign key (author_id) references user(id),
    constraint pk_article_author primary key (article_id, author_id, is_leader, is_corresponding)
);

-- 文献库-文章对应表，二者是多对多的关系，文献库更像是收藏夹
create table category_article(
	category_id int unsigned not null,
	article_id int unsigned not null,
    constraint fk_categoryarticle_article foreign key (article_id) references article(id),
    constraint fk_categoryarticle_category foreign key (category_id) references category(id),
    constraint pk_category_article primary key(category_id, article_id)
);

-- 评论表 
create table if not exists comment(
	comment_id int unsigned primary key auto_increment,
    article_id int unsigned not null comment "评论的论文, 如果评论的是文献库则为-1",
    category_id int unsigned not null comment "评论的是文献库, 如果评论的是文章则为-1",
    critic_id int unsigned not null comment "评论者id",
    critic_email varchar(128) comment "评论者联系方式",
    content varchar(500) not null comment "评论内容",
    update_time datetime not null comment '评论时间, 无需区分创建时间和修改时间',
    parent_id int unsigned not null comment "父评论id, 如果是根结点为-1",
    score numeric(5, 2) comment '对文章打分, 两位小数百分制',
    
    constraint fk_comment_user foreign key (critic_id) references user(id)
);