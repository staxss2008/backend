-- 删除后160个驾驶员（从D043到D202）
DELETE FROM `driver` WHERE `employee_no` >= 'D043' AND `employee_no` <= 'D202';
