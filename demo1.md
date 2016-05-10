#Build a Micro-Service

The goal of this demo is to build a basic micro-service with externalized configuration.

##1 Create Service

1. Create Boot project with Initializer: Web, Actuator, Config Client, Eureka Client
2. Add controller
3. Create Greeting class
4. Create Config class to generate Greeting
5. Add property

###1.1 Actuator

1. Add Actuator to pom
2. Look at env

###1.2 Properties and Profiles

1. Change property at CLI with flag


##2 Setup Config Server

1. Create cna-config project: Web, Config Server
2. Enable server with annotation
3. Add config

Server application.yml
```
server:
  port: 8888
spring:
  cloud:
    config:
      server:
        git:
          uri: ${HOME}/dev/app-config
#          uri: https://github.com/sdeeg-pivotal/app-config
```

4. Enable config client in pom.xml of cna-service
5. Add app identification to bootstrap.yml

pom.xml
```
<!-- add to dependencies block -->
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-config-client</artifactId>
    </dependency>
```
bootstrap.yml
```
spring:
  application:
    name: cna-service
  cloud:
    config:
      uri: ${vcap.services.config-service.credentials.uri:http://localhost:8888}
```
