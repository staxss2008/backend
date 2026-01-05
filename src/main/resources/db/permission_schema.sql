
-- 权限表
CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `permission_name` VARCHAR(50) NOT NULL COMMENT '权限名称',
  `permission_code` VARCHAR(100) NOT NULL COMMENT '权限编码',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '权限描述',
  `permission_type` VARCHAR(20) NOT NULL DEFAULT 'MENU' COMMENT '权限类型：MENU-菜单，BUTTON-按钮',
  `parent_id` BIGINT DEFAULT NULL COMMENT '父权限ID',
  `path` VARCHAR(200) DEFAULT NULL COMMENT '路由路径',
  `component` VARCHAR(200) DEFAULT NULL COMMENT '组件路径',
  `icon` VARCHAR(50) DEFAULT NULL COMMENT '图标',
  `sort_order` INT DEFAULT 0 COMMENT '排序号',
  `status` VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-激活，DISABLED-禁用',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission_code` (`permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `permission_id` BIGINT NOT NULL COMMENT '权限ID',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 插入权限数据
-- 一级菜单
INSERT INTO `sys_permission` (`permission_name`, `permission_code`, `description`, `permission_type`, `parent_id`, `path`, `component`, `icon`, `sort_order`) VALUES
('工作台', 'dashboard', '工作台', 'MENU', NULL, '/dashboard', 'dashboard/Index', 'Monitor', 1),
('调度中心', 'dispatch', '调度中心', 'MENU', NULL, '/dispatch', NULL, 'Position', 2),
('车辆管理', 'vehicle', '车辆管理', 'MENU', NULL, '/vehicle', NULL, 'Van', 3),
('驾驶员管理', 'driver', '驾驶员管理', 'MENU', NULL, '/driver', NULL, 'User', 4),
('用车申请', 'application', '用车申请', 'MENU', NULL, '/application', NULL, 'Document', 5),
('统计分析', 'statistics', '统计分析', 'MENU', NULL, '/statistics', NULL, 'TrendCharts', 6),
('系统管理', 'system', '系统管理', 'MENU', NULL, '/system', NULL, 'Setting', 7);

-- 二级菜单
INSERT INTO `sys_permission` (`permission_name`, `permission_code`, `description`, `permission_type`, `parent_id`, `path`, `component`, `icon`, `sort_order`) VALUES
('实时监控', 'dispatch:monitor', '实时监控', 'MENU', 2, '/dispatch/monitor', 'dispatch/Monitor', 'Odometer', 1),
('调度单管理', 'dispatch:list', '调度单管理', 'MENU', 2, '/dispatch/list', 'dispatch/List', 'List', 2),
('车辆档案', 'vehicle:list', '车辆档案', 'MENU', 3, '/vehicle/list', 'vehicle/List', 'List', 1),
('维修保养', 'vehicle:maintenance', '维修保养', 'MENU', 3, '/vehicle/maintenance', 'vehicle/Maintenance', 'Tools', 2),
('年检保险', 'vehicle:inspection', '年检保险', 'MENU', 3, '/vehicle/inspection', 'vehicle/Inspection', 'Memo', 3),
('驾驶员档案', 'driver:list', '驾驶员档案', 'MENU', 4, '/driver/list', 'driver/List', 'List', 1),
('绩效考核', 'driver:evaluation', '绩效考核', 'MENU', 4, '/driver/evaluation', 'driver/Evaluation', 'ScaleToOriginal', 2),
('申请用车', 'application:create', '申请用车', 'MENU', 5, '/application/create', 'application/Create', 'Edit', 1),
('申请记录', 'application:list', '申请记录', 'MENU', 5, '/application/list', 'application/List', 'List', 2),
('使用统计', 'statistics:usage', '使用统计', 'MENU', 6, '/statistics/usage', 'statistics/Usage', 'DataLine', 1),
('维修统计', 'statistics:maintenance', '维修统计', 'MENU', 6, '/statistics/maintenance', 'statistics/Maintenance', 'DataAnalysis', 2),
('用户管理', 'system:user', '用户管理', 'MENU', 7, '/system/user', 'system/User', 'User', 1),
('角色管理', 'system:role', '角色管理', 'MENU', 7, '/system/role', 'system/Role', 'UserFilled', 2);

-- 分配权限给角色
-- 超级管理员拥有所有权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`)
SELECT 1, id FROM `sys_permission`;

-- 管理员拥有大部分权限（除了系统管理）
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`)
SELECT 2, id FROM `sys_permission` WHERE `permission_code` NOT LIKE 'system:%';

-- 普通用户拥有基本权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`)
SELECT 3, id FROM `sys_permission` WHERE `permission_code` IN (
  'dashboard',
  'dispatch:monitor',
  'vehicle:list',
  'driver:list',
  'application:create',
  'application:list',
  'statistics:usage',
  'statistics:maintenance'
);
