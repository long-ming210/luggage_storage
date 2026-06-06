-- ============================================
-- 行李箱寄存系统 — 数据库建表脚本
-- 数据库：MySQL 8.0+
-- 字符集：utf8mb4
-- 创建日期：2026-06-06
-- ============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS luggage_storage
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE luggage_storage;

-- ============================================
-- 1. 用户表
-- ============================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`            BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '用户ID',
  `username`      VARCHAR(50)   NOT NULL                 COMMENT '用户名，登录用',
  `email`         VARCHAR(100)  NOT NULL                 COMMENT '邮箱',
  `password_hash` VARCHAR(255)  NOT NULL                 COMMENT 'BCrypt加密密码',
  `nickname`      VARCHAR(50)   DEFAULT NULL             COMMENT '昵称',
  `created_at`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `updated_at`    DATETIME      DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email`    (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ============================================
-- 2. 商户表
-- ============================================
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant` (
  `id`            BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '商户ID',
  `username`      VARCHAR(50)   NOT NULL                 COMMENT '登录名',
  `password_hash` VARCHAR(255)  NOT NULL                 COMMENT 'BCrypt加密密码',
  `name`          VARCHAR(100)  NOT NULL                 COMMENT '商户名称',
  `phone`         VARCHAR(20)   DEFAULT NULL             COMMENT '联系电话',
  `created_at`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`    DATETIME      DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_merchant_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商户表';

-- ============================================
-- 3. 寄存点表
-- ============================================
DROP TABLE IF EXISTS `storage_point`;
CREATE TABLE `storage_point` (
  `id`             BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '寄存点ID',
  `merchant_id`    BIGINT        NOT NULL                 COMMENT '所属商户ID',
  `name`           VARCHAR(100)  NOT NULL                 COMMENT '寄存点名称',
  `address`        VARCHAR(500)  NOT NULL                 COMMENT '详细地址',
  `latitude`       DECIMAL(10,7) NOT NULL                 COMMENT '纬度',
  `longitude`      DECIMAL(10,7) NOT NULL                 COMMENT '经度',
  `business_hours` VARCHAR(200)  DEFAULT NULL             COMMENT '营业时间，如 08:00-22:00',
  `status`         TINYINT       NOT NULL DEFAULT 1       COMMENT '状态：0停用 1正常',
  `created_at`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`     DATETIME      DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_location`    (`latitude`, `longitude`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='寄存点表';

-- ============================================
-- 4. 格口表
-- ============================================
DROP TABLE IF EXISTS `locker_slot`;
CREATE TABLE `locker_slot` (
  `id`               BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '格口ID',
  `point_id`         BIGINT        NOT NULL                 COMMENT '所属寄存点ID',
  `slot_number`      VARCHAR(20)   NOT NULL                 COMMENT '格口编号，如 A-01',
  `size_type`        VARCHAR(1)    NOT NULL                 COMMENT '尺寸：S小 M中 L大',
  `price_per_hour`   DECIMAL(10,2) NOT NULL DEFAULT 0.00    COMMENT '每小时价格（元）',
  `status`           VARCHAR(10)   NOT NULL DEFAULT 'idle'  COMMENT '状态：idle空闲 occupied占用 fault故障',
  `locker_device_id` VARCHAR(50)   DEFAULT NULL             COMMENT '物理柜蓝牙设备ID（第二阶段用）',
  `created_at`       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`       DATETIME      DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_point_id`  (`point_id`),
  KEY `idx_status`    (`status`),
  UNIQUE KEY `uk_point_slot` (`point_id`, `slot_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='格口表';

-- ============================================
-- 5. 订单表
-- ============================================
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id`            BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '订单ID',
  `order_no`      VARCHAR(32)   NOT NULL                 COMMENT '订单号，全局唯一',
  `user_id`       BIGINT        NOT NULL                 COMMENT '用户ID',
  `slot_id`       BIGINT        NOT NULL                 COMMENT '格口ID',
  `status`        VARCHAR(10)   NOT NULL DEFAULT 'booked' COMMENT '状态：booked已预约 stored已存入 picked_up已取件 cancelled已取消',
  `book_time`     DATETIME      NOT NULL                 COMMENT '预约时间',
  `store_time`    DATETIME      DEFAULT NULL             COMMENT '实际存入时间',
  `pick_up_time`  DATETIME      DEFAULT NULL             COMMENT '实际取出时间',
  `total_amount`  DECIMAL(10,2) NOT NULL DEFAULT 0.00    COMMENT '总金额（元）',
  `pay_status`    TINYINT       NOT NULL DEFAULT 0       COMMENT '支付状态：0未付 1已付',
  `qr_code`       VARCHAR(255)  DEFAULT NULL             COMMENT '存取二维码内容',
  `created_at`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`    DATETIME      DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_slot_id` (`slot_id`),
  KEY `idx_status`  (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ============================================
-- 初始化测试数据（开发调试用）
-- ============================================

-- 测试用户（密码都是 123456 的 BCrypt 哈希）
INSERT INTO `user` (`username`, `email`, `password_hash`, `nickname`) VALUES
('zhangsan', 'zhangsan@qq.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5Eh', '张三'),
('lisi',     'lisi@qq.com',     '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5Eh', '李四');

-- 测试商户
INSERT INTO `merchant` (`username`, `password_hash`, `name`, `phone`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5Eh', '寄存点管理员', '13800138000');

-- 测试寄存点
INSERT INTO `storage_point` (`merchant_id`, `name`, `address`, `latitude`, `longitude`, `business_hours`) VALUES
(1, '火车站寄存点A', 'XX市火车站北广场出口旁', 39.9042000, 116.4074000, '06:00-23:00'),
(1, '机场寄存点B',   'XX市国际机场T2航站楼1层', 40.0583000, 116.6123000, '00:00-24:00');

-- 测试格口（火车站寄存点 3个，机场寄存点 3个）
INSERT INTO `locker_slot` (`point_id`, `slot_number`, `size_type`, `price_per_hour`) VALUES
(1, 'A-01', 'S', 2.00),
(1, 'A-02', 'M', 3.00),
(1, 'A-03', 'L', 5.00),
(2, 'B-01', 'S', 3.00),
(2, 'B-02', 'M', 5.00),
(2, 'B-03', 'L', 8.00);
