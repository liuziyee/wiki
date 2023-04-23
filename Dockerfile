FROM openjdk:8u191-jre-alpine3.9
RUN mkdir -p /wiki/lib
WORKDIR /wiki
ADD ./target/wiki.jar wiki.jar
# 只会拷贝lib目录里的内容,不会拷贝lib目录
ADD ./target/lib lib/
EXPOSE 80
ENTRYPOINT java -jar -Dspring.profiles.active=dev -Dloader.path=lib -Dserverd.port=80 wiki.jar