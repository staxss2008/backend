-- 修改调度单表中的里程字段精度
ALTER TABLE `dispatch_order` 
MODIFY COLUMN `plan_start_mileage` DECIMAL(12,2) COMMENT '计划开始里程',
MODIFY COLUMN `plan_end_mileage` DECIMAL(12,2) COMMENT '计划结束里程',
MODIFY COLUMN `actual_start_mileage` DECIMAL(12,2) COMMENT '实际开始里程',
MODIFY COLUMN `actual_end_mileage` DECIMAL(12,2) COMMENT '实际结束里程';
