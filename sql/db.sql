CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `username_idx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='用户信息';

INSERT INTO `user` (`id`, `username`, `password`, `create_time`)
VALUES
	(11, 'test', '111', NULL),
	(12, 'test1', '222', NULL),
	(13, 'test2', '333', NULL);


CREATE TABLE `single_chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `other_user_id` bigint(20) DEFAULT NULL COMMENT '对方用户id',
  `is_delete_user` smallint(2) DEFAULT NULL COMMENT '用户是否删除',
  `is_delete_other` smallint(2) DEFAULT NULL COMMENT '对方用户是否删除',
  `create_time` bigint(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  KEY `other_user_id_idx` (`other_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='单聊表';

INSERT INTO `single_chat` (`id`, `user_id`, `other_user_id`, `is_delete_user`, `is_delete_other`, `create_time`)
VALUES
	(11, 11, 12, 0, 0, NULL);


CREATE TABLE `group_chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL DEFAULT '' COMMENT '群聊名称',
  `group_user_id` bigint(20) DEFAULT NULL COMMENT '群主id',
  `other_id` text COMMENT '群内用户',
  `is_delete_user` smallint(2) DEFAULT NULL COMMENT '用户是否删除',
  `is_delete_other` smallint(2) DEFAULT NULL COMMENT '群内用户是否删除',
  `create_time` bigint(32) DEFAULT NULL COMMENT '创建时间毫秒用于排序',
  PRIMARY KEY (`id`),
  KEY `group_user_id_idx` (`group_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='群聊表';


INSERT INTO `group_chat` (`id`, `group_name`, `group_user_id`, `other_id`, `is_delete_user`, `is_delete_other`, `create_time`)
VALUES
	(11, '测试群组1', 11, '11,12', 0, 0, NULL),
	(12, '测试群组2', 11, '11,13', 0, 0, NULL),
	(13, '测试群组3', 11, '11,12,13', 0, 0, NULL);


