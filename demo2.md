#Build a UI app that consumes Micro-services

This exercise builds on the previous one.  We create an application to deliver a JavaScript based 
UI to an end client, and then proxy access to the micro-service via discovery from a service registry.


##3 Setup Service Registry

1. Create cna-registry project: Eureka Server, Config Client
2. Enable server in server app with annotation.
3. Add config to server bootstrap.yml (below)
4. Make the app a registry with @EnableEurekaServer

Server bootstrap.yml
```
spring:
  application:
    name: cna-registry
  cloud:
    config:
      uri: ${vcap.services.config-service.credentials.uri:http://localhost:8888}
```

##4 Register the service

In pom.xml make sure to uncomment:
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>

Add @EnableDiscoveryClient


##5 CNA Client UI

1. Create cna-ui project: Web, Actuator, Config Client, Zuul, Eureka Discovery, Hystrix
2. Set server.port
3. Create index.html
4. Enable Zuul and Discovery

CnaUiApplication.java
```
@EnableDiscoveryClient
@EnableZuulProxy
```

Point to the config server:

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

1. Install Polymer into the app at root (static)

```
bower init
bower install --save Polymer/polymer
bower install --save PolymerElements/iron-ajax
bower install --save PolymerElements/paper-button
```

Build 
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

See that Zuul automatically proxies the request with no server specified.

##6 Change the DOM

Create Message class

```
public class Message {
  public String greeting;
  public String user = "bob";
}
```

Create ClientController.java
```
    @RequestMapping("/message")
    public Message message() {
      return (new RestTemplate()).getForObject("http://localhost:8081/cna-service/greeting", Message.class);
    }
```

##7 Add Circuit Breaker

Create Message service with @EnableCircuitBreaker

```
@Service
public class MessageService {
  
    @Autowired
    private LoadBalancerClient loadBalancer;

  @HystrixCommand(fallbackMethod = "messageFallback")
    public Message getMessage() {
    String url = loadBalancer.choose("cna-service").getUri().toString()+"/greeting";
    Message message = (new RestTemplate()).getForObject(url, Message.class);
    message.greeting += " fold, spindle, mutilate";
    return message;
    }
    
    public Message messageFallback() {
      Message message = new Message();
      message.greeting = "Don't Panic!";
    return message;
  }
}
```
