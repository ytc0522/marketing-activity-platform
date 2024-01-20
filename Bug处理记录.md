1. POM文件改了，maven依赖不刷新？
   在Pom目录下，执行mvn dependency:tree 或mvn dependency:list，
   命令是查看依赖关系，同时，会下载依赖。
2. Sentinel客户端刚开始没有注册到Dashboard上，需要先调用下接口，才会被注册上去。