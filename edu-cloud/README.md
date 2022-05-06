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
    password: sad213ewer.2?sdqw#
    #             sad213ewer.2?sdqw#
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
      keyid: LTAI4GAghQiZ9o7SP2vpz7rV
      keysecret: xB0yOhNMPeefQ8GEal3e8nYQ2M27cL
      bucketname: nwu-edu
  msg:
    sign-name: 我的在线教育平台
    template-code: SMS_204116297

```