#!/bin/bash
# 简单的测试运行脚本 - Linux/Mac版本

echo "========================================"
echo "智能家居系统 JUnit 测试运行器"
echo "========================================"
echo ""

# 检查Maven是否安装
if ! command -v mvn &> /dev/null; then
    echo "[错误] 未找到Maven，请确保Maven已安装并添加到PATH环境变量"
    exit 1
fi

echo "[信息] 正在运行测试..."
echo ""

# 运行测试
mvn -f test-junit/pom.xml test

echo ""
echo "========================================"
echo "测试完成！"
echo "========================================"
echo ""
echo "测试报告位置: test-junit/target/surefire-reports/"
echo ""


