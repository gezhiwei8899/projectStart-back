# projectStart-back
参考start spring io 搞一个一个mybatis的项目启动

项目目录结构：
----src/main/java
    |
     ------------groupId.artifactId
    |                             |
    |                              ---------common
    |                              ---------config
    |                                      |
    |                                       ----------DataSourceAutoConfiguration.java
    |                         --------------dao
    |                                          |
    |                                          -------entity
    |                                          -------mapper
    |                              ----------service
    |                              ----------Artifact.java
    |
    src/main/resource
    |
    ----------------application.properties
    ----------------generatorConfig.xml #mybatis生成文件
    ----------------logback.xml
pom
gitignore
