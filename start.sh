# 镜像名称
IMAGE_NAME="dynamic-datasource"

#容器名称
CONTAINER_NAME="dynamic-datasource-app"

# 版本号
VERSION="1.0.2"

# 端口号
PORT=8080

# git 更新最新代码
git pull

# 获取当前运行中的容器 id
RUNNING_CONTAINER_ID=$(docker ps -q -f name=${CONTAINER_NAME})
# 判断容器是否存在
if [ -n "$RUNNING_CONTAINER_ID" ]; then
    # 存在就关闭并删除容器
    docker kill ${CONTAINER_NAME}
    docker rm ${CONTAINER_NAME}
fi
echo "当前容器已删除，镜像id：${RUNNING_CONTAINER_ID}"


# 重新打包镜像
docker build -t ${IMAGE_NAME}:${VERSION} .

echo "当前容器镜像重新生成，镜像id：$(docker images -q ${IMAGE_NAME})"
# 启动容器
docker run -d -p ${PORT}:${PORT} --name ${CONTAINER_NAME} ${IMAGE_NAME}:${VERSION}

echo "当前容器已更新，镜像id：${IMAGE_ID}，版本号：${VERSION}"