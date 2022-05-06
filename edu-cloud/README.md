云服务

```yaml
server:
  port: 8002
spring:
  application:
    name: service-cloud
  cloud:
    nacos:
      discovery:
        server-addr: 110.42.169.135:8848
  profiles:
    active: dev
  servlet:
    multipart:
      max-file-size: 1024MB
      max-request-size: 1024MB
  redis:
    port: 6379
    password: xxx
    host: 110.42.169.135
    lettuce:
      pool:
        max-active: 8
        min-idle: 8
        max-idle: 8
        max-wait: 1000
      shutdown-timeout: 100
  #阿里云 OSS
  #不同的服务器，地址不同
aliyun:
  oss:
    file:
      endpoint: oss-cn-beijing.aliyuncs.com
      keyid: xxx
      keysecret: xxx
      bucketname: nwu-edu
  msg:
    sign-name: 我的在线教育平台
    template-code: xxx

```