-- 删除现有的驾驶员用户（从D004到D042）
DELETE FROM `sys_user_role` WHERE user_id IN (SELECT id FROM `sys_user` WHERE username IN ('D004', 'D005', 'D006', 'D007', 'D008', 'D009', 'D010', 'D011', 'D012', 'D013', 'D014', 'D015', 'D016', 'D017', 'D018', 'D019', 'D020', 'D021', 'D022', 'D023', 'D024', 'D025', 'D026', 'D027', 'D028', 'D029', 'D030', 'D031', 'D032', 'D033', 'D034', 'D035', 'D036', 'D037', 'D038', 'D039', 'D040', 'D041', 'D042'));

DELETE FROM `sys_user` WHERE username IN ('D004', 'D005', 'D006', 'D007', 'D008', 'D009', 'D010', 'D011', 'D012', 'D013', 'D014', 'D015', 'D016', 'D017', 'D018', 'D019', 'D020', 'D021', 'D022', 'D023', 'D024', 'D025', 'D026', 'D027', 'D028', 'D029', 'D030', 'D031', 'D032', 'D033', 'D034', 'D035', 'D036', 'D037', 'D038', 'D039', 'D040', 'D041', 'D042'');

-- 为所有驾驶员创建用户账号
-- 用户名是驾驶员的工号，密码统一为111111（不包括双引号）

-- 为驾驶员创建用户账号并分配普通用户角色
-- 密码统一设置为111111（实际应用中应该使用加密存储）

-- 为Zhang Wei创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D004', '111111', 'Zhang Wei', '13900139001', '运营部', '驾驶员');

-- 为Li Na创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D005', '111111', 'Li Na', '13900139002', '运营部', '驾驶员');

-- 为Wang Qiang创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D006', '111111', 'Wang Qiang', '13900139003', '运营部', '驾驶员');

-- 为Liu Yang创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D007', '111111', 'Liu Yang', '13900139004', '运营部', '驾驶员');

-- 为Chen Jing创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D008', '111111', 'Chen Jing', '13900139005', '运营部', '驾驶员');

-- 为Yang Min创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D009', '111111', 'Yang Min', '13900139006', '运营部', '驾驶员');

-- 为Zhao Lei创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D010', '111111', 'Zhao Lei', '13900139007', '运营部', '驾驶员');

-- 为Sun Wei创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D011', '111111', 'Sun Wei', '13900139008', '运营部', '驾驶员');

-- 为Zhou Jie创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D012', '111111', 'Zhou Jie', '13900139009', '运营部', '驾驶员');

-- 为Wu Hao创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D013', '111111', 'Wu Hao', '13900139010', '运营部', '驾驶员');

-- 为Zheng Feng创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D014', '111111', 'Zheng Feng', '13900139011', '运营部', '驾驶员');

-- 为Xu Gang创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D015', '111111', 'Xu Gang', '13900139012', '运营部', '驾驶员');

-- 为Ma Jun创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D016', '111111', 'Ma Jun', '13900139013', '运营部', '驾驶员');

-- 为Fang Hui创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D017', '111111', 'Fang Hui', '13900139014', '运营部', '驾驶员');

-- 为Gao Tao创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D018', '111111', 'Gao Tao', '13900139015', '运营部', '驾驶员');

-- 为Lin Ping创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D019', '111111', 'Lin Ping', '13900139016', '运营部', '驾驶员');

-- 为He Yan创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D020', '111111', 'He Yan', '13900139017', '运营部', '驾驶员');

-- 为Luo Wei创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D021', '111111', 'Luo Wei', '13900139018', '运营部', '驾驶员');

-- 为Song Jie创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D022', '111111', 'Song Jie', '13900139019', '运营部', '驾驶员');

-- 为Xie Ming创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D023', '111111', 'Xie Ming', '13900139020', '运营部', '驾驶员');

-- 为Tang Wei创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D024', '111111', 'Tang Wei', '13900139021', '运营部', '驾驶员');

-- 为Feng Li创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D025', '111111', 'Feng Li', '13900139022', '运营部', '驾驶员');

-- 为Ding Qiang创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D026', '111111', 'Ding Qiang', '13900139023', '运营部', '驾驶员');

-- 为Yu Hui创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D027', '111111', 'Yu Hui', '13900139024', '运营部', '驾驶员');

-- 为Xiao Feng创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D028', '111111', 'Xiao Feng', '13900139025', '运营部', '驾驶员');

-- 为Cao Wei创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D029', '111111', 'Cao Wei', '13900139026', '运营部', '驾驶员');

-- 为Peng Tao创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D030', '111111', 'Peng Tao', '13900139027', '运营部', '驾驶员');

-- 为Lu Ming创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D031', '111111', 'Lu Ming', '13900139028', '运营部', '驾驶员');

-- 为Jian Wei创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D032', '111111', 'Jian Wei', '13900139029', '运营部', '驾驶员');

-- 为Jiang Li创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D033', '111111', 'Jiang Li', '13900139030', '运营部', '驾驶员');

-- 为Yan Jun创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D034', '111111', 'Yan Jun', '13900139031', '运营部', '驾驶员');

-- 为Xu Yang创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D035', '111111', 'Xu Yang', '13900139032', '运营部', '驾驶员');

-- 为Qian Wei创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D036', '111111', 'Qian Wei', '13900139033', '运营部', '驾驶员');

-- 为Du Feng创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D037', '111111', 'Du Feng', '13900139034', '运营部', '驾驶员');

-- 为Shen Hui创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D038', '111111', 'Shen Hui', '13900139035', '运营部', '驾驶员');

-- 为Han Tao创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D039', '111111', 'Han Tao', '13900139036', '运营部', '驾驶员');

-- 为Yang Wei创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D040', '111111', 'Yang Wei', '13900139037', '运营部', '驾驶员');

-- 为Cui Ming创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D041', '111111', 'Cui Ming', '13900139038', '运营部', '驾驶员');

-- 为Pan Wei创建用户账号
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `department`, `position`) VALUES
('D042', '111111', 'Pan Wei', '13900139039', '运营部', '驾驶员');

-- 为驾驶员用户分配普通用户角色（role_id=3）
INSERT INTO `sys_user_role` (`user_id`, `role_id`)
SELECT u.id, 3
FROM `sys_user` u
WHERE u.username IN ('D004', 'D005', 'D006', 'D007', 'D008', 'D009', 'D010', 'D011', 'D012', 'D013', 'D014', 'D015', 'D016', 'D017', 'D018', 'D019', 'D020', 'D021', 'D022', 'D023', 'D024', 'D025', 'D026', 'D027', 'D028', 'D029', 'D030', 'D031', 'D032', 'D033', 'D034', 'D035', 'D036', 'D037', 'D038', 'D039', 'D040', 'D041', 'D042');
