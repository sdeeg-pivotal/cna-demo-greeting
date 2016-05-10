#Build a UI app that consumes Micro-services

This exercise builds on the previous one.  We create an application to deliver a JavaScript based 
UI to an end client, and then look up the micro-service from a service registry.


##3 Setup Service Registry

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

##4 CNA Client UI

1. Create cna-ui project: Web, Actuator, Config Client, Zuul, Eureka Discovery, Hystrix
2. Set server.port
3. Create index.html
4. Enable Zuul and Discovery

CnaUiApplication.java
```
@EnableDiscoveryClient
@EnableZuulProxy
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

###4.1 Create the UI

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

