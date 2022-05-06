```yaml
server:
  port: 9001
spring:
  application:
    name: service-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 110.42.169.135:8848
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
      - id: service-base
        uri: lb://service-base
        predicates:
        - Path=/base/**
      - id: service-course
        uri: lb://service-course
        predicates:
        - Path=/course/**
      - id: service-cloud
        uri: lb://service-cloud
        predicates:
        - Path=/cloud/**
      - id: service-extra
        uri: lb://service-extra
        predicates:
        - Path=/extra/**
      - id: service-recommend
        uri: lb://service-recommend
        predicates:
        - Path=/recommend/**
      - id: service-statistic
        uri: lb://service-statistic
        predicates:
        - Path=/statistic/**
logging:
  level:
    root: info
    com:
      alibaba:
        nacos:
          client:
            naming: error
```