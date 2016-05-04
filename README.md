##CNA Demo Notes

#1 Create Service

1. Create Boot project with Initializer: Web, Actuator, Config Client, Eureka Client
2. Add controller
3. Create Greeting class
4. Create Config class to generate Greeting
5. Add property

##1.1 Actuator

1. Add Actuator to pom
2. Look at env

##1.2 Properties and Profiles

1. Change property at CLI with flag

#2 Setup Config Server

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

#Eureka Server

1. Create cna-registry project: Eureka Server, Config Client
2. Enable server in server app with annotation.
3. Add config to server bootstrap.yml (below)
4. Enable cna-service with @EnableDiscoveryClient

Server bootstrap.yml
```
spring:
  application:
    name: cna-registry
  cloud:
    config:
      uri: ${vcap.services.config-service.credentials.uri:http://localhost:8888}
```

#CNA Client UI

1. Create cna-ui project: Web, Actuator, Config Client, Zuul, Eureka Discovery, Hystrix
2. Set server.port
3. Create index.html
4. Enable Zuul and Discovery

CnaUiApplication.java
```
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class CnaUiApplication {
...
```

bootstrap.yml
```
spring:
  application:
    name: cna-ui
  cloud:
    config:
      uri: ${vcap.services.config-service.credentials.uri:http://localhost:8888}
```

##Create the UI

5. Install Polymer into the app at root (static)

```
bower init
bower install --save Polymer/polymer
bower install --save PolymerElements/iron-ajax
bower install --save PolymerElements/paper-button
```

6. Add the elements directory
7. Create message-display.html
8. Update index.html

index.html
```
<!DOCTYPE html>
<html>
  <head>
    <script src="bower_components/webcomponentsjs/webcomponents-lite.min.js"></script>
    <link rel="import" href="elements/message-display.html">
  </head>
  <body>
    <message-display></message-display>
  </body>
</html>
```

Create ClientController.java
```
    public String messageFallback() { 
      return "Don't panic";
    }
    
    @HystrixCommand(fallbackMethod = "messageFallback")
    @RequestMapping("/message")
    public String message() {
      return (new RestTemplate()).getForObject("http://localhost:8080/greeting", String.class);
    }
```

