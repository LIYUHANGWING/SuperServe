#!/bin/bash

# 检查Nginx是否已安装
if ! command -v nginx &> /dev/null; then
    echo "Nginx未安装，请先安装Nginx"
    exit 1
fi

# 检查Nginx配置
echo "检查Nginx配置..."
nginx -t

# 启动Nginx
echo "启动Nginx..."
nginx

# 启动Spring Boot应用
echo "启动Spring Boot应用..."
java -jar target/spring-mybatis-0.0.1-SNAPSHOT.jar 