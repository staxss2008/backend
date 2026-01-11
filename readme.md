# 派车管理系统后端

基于Spring Boot 3.2的派车管理系统后端服务，采用前后端分离架构，提供RESTful API接口。

## 技术栈

- **框架**: Spring Boot 3.2.0
- **ORM**: MyBatis Plus 3.5.6
- **数据库**: MySQL 8.0.33
- **缓存**: Redis 7.0 (可选)
- **安全**: Spring Security + JWT 0.11.5
- **API文档**: Swagger 3.0.0
- **构建工具**: Maven 3.6+
- **JDK版本**: JDK 21

## 功能模块

1. **认证授权**: 用户登录、JWT令牌管理、权限控制
2. **车辆管理**: 车辆档案、状态管理、维修记录
3. **驾驶员管理**: 驾驶员档案、状态管理
4. **调度管理**: 调度单管理、智能调度推荐、实时位置监控
5. **用车申请**: 申请提交、审批流程、车辆指派
6. **系统管理**: 用户管理、角色管理、权限管理

## 环境要求

- JDK 21+
- Maven 3.6+
- MySQL 8.0+
- Redis 7.0+ (可选)

## 快速开始

### 1. 配置数据库

修改 `src/main/resources/application.yml` 中的数据库配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/vehicle_management?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: your_password
```

### 2. 初始化数据库

执行数据库初始化脚本：

```bash
# 方式一：使用批处理脚本
cd ..
init_database.bat

# 方式二：手动执行SQL
mysql -u root -p < src/main/resources/init.sql
```

### 3. 配置Redis (可选)

如果需要启用Redis缓存，修改 `application.yml`：

```yaml
spring:
  redis:
    enabled: true
    host: localhost
    port: 6379
    password: your_password
```

### 4. 启动项目

```bash
# 方式一：使用Maven命令
mvn clean install
mvn spring-boot:run

# 方式二：使用IDEA
直接运行 VehicleManagementApplication 主类
```

### 5. 访问API文档

启动成功后，访问Swagger文档：

```
http://localhost:8080/vehicle-management/swagger-ui.html
```

## 项目结构

```
backend/
├── src/main/java/com/vehicle/management/
│   ├── annotation/        # 自定义注解
│   ├── config/            # 配置类
│   ├── controller/        # 控制器层
│   ├── dto/               # 数据传输对象
│   ├── entity/            # 实体类
│   ├── exception/         # 异常处理
│   ├── interceptor/       # 拦截器
│   ├── mapper/            # 数据访问层
│   ├── service/           # 服务层
│   │   └── impl/          # 服务实现
│   ├── util/              # 工具类
│   ├── vo/                # 视图对象
│   └── VehicleManagementApplication.java
└── src/main/resources/
    ├── application.yml    # 配置文件
    └── db/               # 数据库脚本
```

## API接口说明

### 基础路径

所有API接口的基础路径为：`/vehicle-management/api`

### 认证方式

使用JWT进行身份认证，请求头需要携带：

```
Authorization: Bearer {token}
```

### 主要接口

#### 认证相关
```
POST   /auth/login              # 用户登录
POST   /auth/logout             # 用户登出
POST   /auth/refresh-token      # 刷新令牌
```

#### 车辆管理
```
GET    /vehicle/list            # 获取车辆列表
POST   /vehicle/add             # 新增车辆
PUT    /vehicle/update/{id}     # 更新车辆
PUT    /vehicle/update-status/{id}  # 更新车辆状态
DELETE /vehicle/delete/{id}     # 删除车辆
GET    /vehicle/stats           # 获取车辆统计
```

#### 驾驶员管理
```
GET    /driver/list             # 获取驾驶员列表
POST   /driver/add              # 新增驾驶员
PUT    /driver/update/{id}      # 更新驾驶员
PUT    /driver/update-status/{id}  # 更新驾驶员状态
DELETE /driver/delete/{id}      # 删除驾驶员
```

#### 调度管理
```
POST   /dispatch/create-order        # 创建调度单
POST   /dispatch/smart-dispatch      # 智能调度推荐
GET    /dispatch/monitor-data        # 获取监控数据
GET    /dispatch/real-time-location  # 获取实时位置
GET    /dispatch/list                # 获取调度列表
POST   /dispatch/update-start-mileage  # 更新开始里程
POST   /dispatch/update-end-mileage    # 更新结束里程
```

#### 用车申请
```
GET    /application/list              # 获取申请列表
POST   /application/create            # 创建申请
PUT    /application/approve/{id}     # 审批通过
PUT    /application/reject/{id}      # 审批拒绝
GET    /application/detail/{id}      # 获取申请详情
POST   /application/assign-vehicle/{id}  # 指派车辆
PUT    /application/complete/{id}   # 完成申请
```

#### 系统管理
```
# 用户管理
GET    /user/list              # 获取用户列表
POST   /user/add               # 新增用户
PUT    /user/update/{id}       # 更新用户
PUT    /user/update-status/{id} # 更新用户状态
DELETE /user/delete/{id}       # 删除用户
POST   /user/assign-roles      # 分配角色
GET    /user/roles/{userId}    # 获取用户角色
POST   /user/change-password    # 修改密码

# 角色管理
GET    /role/list              # 获取角色列表
GET    /role/all               # 获取所有角色
POST   /role/add               # 新增角色
PUT    /role/update/{id}       # 更新角色
PUT    /role/update-status/{id} # 更新角色状态
DELETE /role/delete/{id}       # 删除角色
```

## 开发规范

### 代码规范

1. **包命名规范**
   - 使用小写字母
   - 使用点分隔符
   - 示例：`com.vehicle.management.controller`

2. **类命名规范**
   - 使用大驼峰命名法
   - 类名应该是名词
   - 示例：`UserController`, `VehicleService`

3. **方法命名规范**
   - 使用小驼峰命名法
   - 方法名应该是动词
   - 示例：`getUserList()`, `addVehicle()`

4. **常量命名规范**
   - 使用全大写字母
   - 使用下划线分隔
   - 示例：`MAX_PAGE_SIZE`, `DEFAULT_TIMEOUT`

### API接口规范

1. **RESTful设计**
   - GET: 获取资源
   - POST: 创建资源
   - PUT: 更新资源
   - DELETE: 删除资源

2. **响应格式**
```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

3. **状态码说明**
   - 200: 请求成功
   - 400: 请求参数错误
   - 401: 未授权
   - 403: 禁止访问
   - 404: 资源不存在
   - 500: 服务器内部错误

## 部署说明

### 本地部署

```bash
# 打包项目
mvn clean package

# 运行war包
java -jar target/vehicle-management-1.0.0.war
```

### Tomcat部署

```bash
# 使用部署脚本
cd ..
deploy_tomcat.bat

# 手动部署
1. 将target/vehicle-management-1.0.0.war复制到Tomcat的webapps目录
2. 启动Tomcat
```

详细部署说明请参考项目根目录的 [tomcat_deploy.md](../tomcat_deploy.md)

## 常见问题

### 1. 数据库连接失败

检查 `application.yml` 中的数据库配置是否正确，确保MySQL服务已启动。

### 2. Redis连接失败

如果启用了Redis但连接失败，可以暂时禁用Redis：

```yaml
spring:
  redis:
    enabled: false
```

### 3. 端口被占用

修改 `application.yml` 中的端口号：

```yaml
server:
  port: 8081
```

## 许可证

本项目采用MIT许可证。
