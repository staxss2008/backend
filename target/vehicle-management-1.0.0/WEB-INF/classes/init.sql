-- 删除已存在的表
DROP TABLE IF EXISTS `dispatch_order`;
DROP TABLE IF EXISTS `maintenance_record`;
DROP TABLE IF EXISTS `trip_application`;
DROP TABLE IF EXISTS `driver`;
DROP TABLE IF EXISTS `vehicle`;

-- 车辆表
CREATE TABLE `vehicle` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `plate_no` VARCHAR(20) NOT NULL COMMENT '车牌号',
  `vehicle_type` VARCHAR(20) NOT NULL COMMENT '车辆类型',
  `brand` VARCHAR(50) COMMENT '品牌',
  `model` VARCHAR(50) COMMENT '型号',
  `color` VARCHAR(20) COMMENT '颜色',
  `seats` INT DEFAULT 5 COMMENT '座位数',
  `fuel_type` VARCHAR(20) COMMENT '燃料类型',
  `buy_date` DATE COMMENT '购置日期',
  `status` VARCHAR(20) DEFAULT 'IDLE' COMMENT '状态：IDLE-空闲,BUSY-使用中,MAINTENANCE-维修',
  `gps_device_id` VARCHAR(50) COMMENT 'GPS设备ID',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_plate_no` (`plate_no`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆信息表';

-- 驾驶员表
CREATE TABLE `driver` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `employee_no` VARCHAR(20) COMMENT '工号',
  `phone` VARCHAR(20) COMMENT '手机号',
  `license_no` VARCHAR(50) COMMENT '驾驶证号',
  `license_type` VARCHAR(20) COMMENT '准驾车型',
  `license_expire` DATE COMMENT '驾照有效期',
  `status` VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '状态',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='驾驶员表';

-- 用车申请表
CREATE TABLE `trip_application` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `application_no` VARCHAR(50) NOT NULL COMMENT '申请单号',
  `applicant_id` BIGINT NOT NULL COMMENT '申请人ID',
  `applicant_name` VARCHAR(50) COMMENT '申请人姓名',
  `department_id` BIGINT COMMENT '部门ID',
  `department_name` VARCHAR(100) COMMENT '部门名称',
  `purpose` VARCHAR(500) NOT NULL COMMENT '用车事由',
  `trip_type` VARCHAR(20) COMMENT '用车类型：OFFICIAL-公务,TRANSPORT-通勤,RECEIVE-接待',
  `passenger_count` INT DEFAULT 1 COMMENT '乘车人数',
  `start_time` DATETIME NOT NULL COMMENT '计划开始时间',
  `end_time` DATETIME NOT NULL COMMENT '计划结束时间',
  `start_location` VARCHAR(200) COMMENT '出发地点',
  `end_location` VARCHAR(200) COMMENT '目的地',
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态',
  `current_approver` VARCHAR(50) COMMENT '当前审批人',
  `remark` VARCHAR(500) COMMENT '备注',
  `vehicle_id` BIGINT COMMENT '车辆ID',
  `driver_id` BIGINT COMMENT '驾驶员ID',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_application_no` (`application_no`),
  INDEX `idx_applicant_id` (`applicant_id`),
  INDEX `idx_status` (`status`),
  INDEX `idx_vehicle_id` (`vehicle_id`),
  INDEX `idx_driver_id` (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用车申请表';

-- 维修记录表
CREATE TABLE `maintenance_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `vehicle_id` BIGINT NOT NULL COMMENT '车辆ID',
  `plate_no` VARCHAR(20) NOT NULL COMMENT '车牌号',
  `type` VARCHAR(20) NOT NULL COMMENT '维修类型',
  `date` DATE NOT NULL COMMENT '维修日期',
  `mileage` DECIMAL(10,2) COMMENT '维修时里程',
  `cost` DECIMAL(10,2) COMMENT '维修费用',
  `content` TEXT COMMENT '维修内容',
  `provider` VARCHAR(100) COMMENT '维修服务商',
  `handler` VARCHAR(50) COMMENT '经办人',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_vehicle_id` (`vehicle_id`),
  INDEX `idx_plate_no` (`plate_no`),
  INDEX `idx_date` (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修记录表';

-- 调度单表
CREATE TABLE `dispatch_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_no` VARCHAR(50) NOT NULL COMMENT '调度单号',
  `application_id` BIGINT NOT NULL COMMENT '关联申请ID',
  `vehicle_id` BIGINT NOT NULL COMMENT '车辆ID',
  `driver_id` BIGINT NOT NULL COMMENT '驾驶员ID',
  `dispatcher_id` BIGINT COMMENT '调度员ID',
  `dispatch_time` DATETIME COMMENT '调度时间',
  `actual_start_time` DATETIME COMMENT '实际开始时间',
  `actual_end_time` DATETIME COMMENT '实际结束时间',
  `plan_start_mileage` DECIMAL(10,2) COMMENT '计划开始里程',
  `plan_end_mileage` DECIMAL(10,2) COMMENT '计划结束里程',
  `actual_start_mileage` DECIMAL(10,2) COMMENT '实际开始里程',
  `actual_end_mileage` DECIMAL(10,2) COMMENT '实际结束里程',
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  INDEX `idx_vehicle_id` (`vehicle_id`),
  INDEX `idx_driver_id` (`driver_id`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='调度单表';

-- 插入示例数据
-- 车辆数据
INSERT INTO `vehicle` (`plate_no`, `vehicle_type`, `brand`, `model`, `color`, `seats`, `fuel_type`, `buy_date`, `status`, `gps_device_id`) VALUES
('JINGA12345', 'CAR', 'Toyota', 'Camry', 'White', 5, 'GASOLINE', '2022-01-15', 'IDLE', 'GPS001'),
('JINGB67890', 'SUV', 'Honda', 'CR-V', 'Black', 7, 'GASOLINE', '2021-08-20', 'BUSY', 'GPS002'),
('JINGC11111', 'VAN', 'Buick', 'GL8', 'Silver', 7, 'GASOLINE', '2020-05-10', 'MAINTENANCE', 'GPS003'),
('JINGD22222', 'TRUCK', 'Foton', 'Aumark', 'Blue', 3, 'DIESEL', '2023-02-28', 'IDLE', 'GPS004');

-- 驾驶员数据
INSERT INTO `driver` (`name`, `employee_no`, `phone`, `license_no`, `license_type`, `license_expire`, `status`) VALUES
('Zhang San', 'D001', '13800138001', '110101199001011234', 'C1', '2028-05-20', 'ACTIVE'),
('Li Si', 'D002', '13800138002', '110101198502022345', 'A1', '2027-12-15', 'ACTIVE'),
('Wang Wu', 'D003', '13800138003', '110101198803033456', 'B2', '2026-08-10', 'INACTIVE');

-- 用车申请数据
INSERT INTO `trip_application` (`application_no`, `applicant_id`, `applicant_name`, `department_name`, `purpose`, `trip_type`, `passenger_count`, `start_time`, `end_time`, `start_location`, `end_location`, `status`) VALUES
('SQ20231231001', 1001, 'Zhao Liu', 'Administration', 'Airport pickup', 'RECEIVE', 3, '2023-12-31 09:00:00', '2023-12-31 12:00:00', 'Headquarters', 'Airport', 'APPROVED'),
('SQ20231231002', 1002, 'Qian Qi', 'Sales', 'Client visit', 'OFFICIAL', 2, '2023-12-31 14:00:00', '2023-12-31 17:00:00', 'Headquarters', 'Client Company', 'PENDING');

-- 调度单数据
INSERT INTO `dispatch_order` (`order_no`, `application_id`, `vehicle_id`, `driver_id`, `dispatcher_id`, `dispatch_time`, `status`) VALUES
('PD20231231001', 1, 2, 1, 100, '2023-12-31 08:30:00', 'IN_PROGRESS'),
('PD20231231002', 2, 1, 2, 100, '2023-12-31 13:30:00', 'PENDING');