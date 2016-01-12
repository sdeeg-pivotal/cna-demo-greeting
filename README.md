##CNA Demo Notes

#1 Create Service

Boot project
Add controller
Create Greeting
Create Config class
Add property
Change return type in controller to Greeting


#2 Setup Config Server

Enable server with annotation

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

Client bootstrap.yml
```
spring:
  application:
    name: app-name
  cloud:
    config:
      uri: ${vcap.services.config-service.credentials.uri:http://localhost:8888}
```

#Eureka Server

Enable server in server app with annotation.
Enable client with @EnableDiscoveryClient

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

cna-ui

enable auto-proxy
```
@EnableZuulProxy
@EnableDiscoveryClient
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

Install Polymer into the app at root (static)
```
bower init
bower install --save Polymer/polymer#^1.2.0
bower install --save PolymerElements/iron-ajax
bower install --save PolymerElements/paper-button
```

Add the elements directory and element.

Create the UI index.html
```
<!DOCTYPE html>
<html>
  <head>
    <script src="bower_components/webcomponentsjs/webcomponents-lite.min.js"></script>
    <link rel="import" href="elementes/message-display.html">
  </head>
  <body>
    <message-display></message-display>
  </body>
</html>
```

Create elements directory and add message-display.html

Create ClientController.java
```
  @Autowired
  private RestTemplate restTemplate;
  
  public String messageFallback() { 
    return "Don't panic";
  }
  
  @HystrixCommand(fallbackMethod = "messageFallback")
  @RequestMapping("/message")
  public String message() {
    return restTemplate.getForObject("http://cna-service/greeting", String.class);
  }
```

