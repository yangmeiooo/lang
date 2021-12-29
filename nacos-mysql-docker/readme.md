- 创建 logs，init.d 挂载目录

- 修改 custom.properties 文件

- enter command

  ```bash
  docker  run --name nacos -p 8848:8848   --privileged=true --restart=always -e JVM_XMS=256m -e JVM_XMX=256m -e MODE=standalone -e PREFER_HOST_MODE=hostname -v /mydata/nacos/logs:/d/nacos/logs -v /mydata/nacos/init.d/custom.properties:/d/nacos/init.d/custom.properties -d nacos/nacos-server:2.0.3
  ```

  

