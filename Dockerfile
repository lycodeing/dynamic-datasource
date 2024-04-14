FROM jdk8-maven AS builder

# 设置工作目录
WORKDIR /builder

# 复制代码和pom文件到镜像中
COPY src /builder/src
COPY pom.xml /builder

# maven打包jar

RUN  mvn package -DskipTests


FROM openjdk:8-jdk AS runing

WORKDIR /app

COPY --from=builder /builder/target/dynamic-datasource-1.0.jar /app/dynamic-datasource.jar

CMD ["java", "-jar", "dynamic-datasource.jar"]