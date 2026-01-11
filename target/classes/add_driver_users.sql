-- 为驾驶员创建用户账号并分配普通用户角色
-- 密码统一设置为123456（实际应用中应该使用加密存储）

-- 为Zhang San创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('driver001', '123456', 'Zhang San', '13800138001', '运营部', '驾驶员');

-- 为Li Si创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('driver002', '123456', 'Li Si', '13800138002', '运营部', '驾驶员');

-- 为Wang Wu创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('driver003', '123456', 'Wang Wu', '13800138003', '运营部', '驾驶员');

-- 为驾驶员用户分配普通用户角色（role_id=3）
-- 获取刚创建的驾驶员用户ID并分配角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`) 
SELECT u.id, 3 
FROM `sys_user` u 
WHERE u.username IN ('driver001', 'driver002', 'driver003');
