@echo off
echo 正在更新驾驶员数据和用户账号...

echo 步骤1: 删除后160个驾驶员（从D043到D202）
mysql -u root -p your_password vehicle_management < delete_drivers.sql

echo 步骤2: 插入40位驾驶员数据
mysql -u root -pyour_password vehicle_management < insert_40_drivers.sql

echo 步骤3: 为40位驾驶员创建用户账号
mysql -u root -pyour_password vehicle_management < create_driver_users_new.sql

echo 驾驶员数据和用户账号更新完成！
pause
