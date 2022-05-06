包含了系统中的关键服务，比如

用户管理、权限管理、注册登录管理、院系管理、类别管理

```yaml
server:
  port: 8001
spring:
  application:
    name: service-base
  cloud:
    nacos:
      discovery:
        server-addr: 110.42.169.135:8848
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/edu_user?serverTimezone=Asia/Shanghai
    username: root
    password: 123456
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.nwu.base.entity
  global-config:
    db-config:
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
# 开启熔断机制
feign:
  hystrix:
    enabled: true
# 设置hystrix超时时间，默认1000ms
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 6000
logging:
  level:
    root: info
    com:
      alibaba:
        nacos:
          client:
            naming: error
```
