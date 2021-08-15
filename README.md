#  Readme

java web 项目

## web_weibo

- [使用说明](#使用说明)

## 使用说明

```bash
# 编译
javac --class-path "libs/freemarker.jar:libs/mysql-connector-java-8.0.11.jar" -encoding "UTF-8"  -d out/production/web_weibo --source-path src src/Server.java
# 前台运行
java --class-path "libs/freemarker.jar:libs/mysql-connector-java-8.0.11.jar:out/production/web_weibo" -D"file.encoding=UTF-8" Server
# 后台运行，并记录日志
nohup java --class-path "libs/freemarker.jar:libs/mysql-connector-java-8.0.11.jar:out/production/web_weibo" -D"file.encoding=UTF-8" Server &
```

