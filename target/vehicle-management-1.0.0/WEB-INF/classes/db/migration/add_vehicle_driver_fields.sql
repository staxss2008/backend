
-- 为trip_application表添加车辆和驾驶员字段
ALTER TABLE trip_application ADD COLUMN vehicle_id BIGINT COMMENT '车辆ID';
ALTER TABLE trip_application ADD COLUMN driver_id BIGINT COMMENT '驾驶员ID';
