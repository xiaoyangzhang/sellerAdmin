CREATE DATABASE `selleradmin` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE `selleradmin`.`order_operation_log` (
	`id` bigint(32) NOT NULL AUTO_INCREMENT,
	`biz_no` varchar(1000) DEFAULT '' COMMENT '业务号',
	`operation_id` bigint(32) DEFAULT 0 COMMENT '操作人id',
	`status` int(4) DEFAULT 0 COMMENT '状态',
	`content` varchar(200) DEFAULT '' COMMENT '修改内容',
	`desc` varchar(1000) DEFAULT '' COMMENT '备注',
	`gmt_created` datetime NOT NULL COMMENT '创建时间',
	`gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
) COMMENT='订单修改记录表';
--test
