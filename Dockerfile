# 使用官方OpenJDK 21作为基础镜像
FROM openjdk:21-jre-slim

# 设置工作目录
WORKDIR /app

# 复制jar文件到容器中
COPY target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "/app/app.jar"]