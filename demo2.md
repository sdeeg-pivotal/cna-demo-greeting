#Build a UI app that consumes Micro-services

#Needs Updating!!

This exercise builds on the projects build in demo1.  We create an application to deliver a JavaScript based 
UI to an end client, and then proxy access to the micro-service via discovery from a service registry.  Next
we add the Hystrix circuit breaker to create fallback behavior if the service is not available.


##3 Setup Service Registry

1. Create cna-registry project: Eureka Server, Config Client
2. Enable server in server app with @EnableEurekaServer
3. Add config to server bootstrap.yml (below)

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
```
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
```

Add @EnableDiscoveryClient


##5 CNA Client UI

1. Create cna-ui project: Web, Actuator, Config Client, Zuul, Eureka Discovery, Hystrix
2. Create index.html
3. Enable Zuul and Discovery

CnaUiApplication.java
```
@EnableDiscoveryClient
@EnableZuulProxy
```

Set the properties to point to the config server:

bootstrap.yml
```
spring:
  application:
    name: cna-ui
  cloud:
    config:
      uri: ${vcap.services.config-service.credentials.uri:http://localhost:8888}
```

### Create the UI

1. Create index.html in web root (static)
2. Install Polymer into the app at web root (static)

```
bower init

bower install --save Polymer/polymer
bower install --save PolymerElements/iron-ajax
bower install --save PolymerElements/paper-button
```

1. Add the elements directory
2. Create message-display.html
3. Update index.html

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

##6 Setup a Bounded Context

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

Create MessageService with @EnableCircuitBreaker

```
  @Autowired
  private LoadBalancerClient loadBalancer;

  @HystrixCommand(fallbackMethod = "messageFallback")
  public Message getMessage() {
    String url = loadBalancer.choose("cna-service").getUri().toString() + "/greeting";
    Message message = (new RestTemplate()).getForObject(url, Message.class);
    return message;
  }

  public Message messageFallback() {
    Message message = new Message();
    message.greeting = "Don't Panic!";
    return message;
  }
```
