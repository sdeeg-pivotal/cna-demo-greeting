#CNA Demo Notes

The instructions in the demo script pags are out of date with the code.

All the projects have been updated
to consume Spring Cloud Services hosted on PCF.  All use Boot 1.4.2 and Spring Cloud Camden.SR3.  Sleuth/Zipkin has also been added.  I also updated many of the annotations (@RequestMapping -> @GetMapping) and use constructor injection instead of @Autowire.

##Using the projects

Load them into STS via import ... Existing Maven projects.  Make any tweaks you need to (EG: changing the location for the config server git repo), and launch from the Boot Dashboard.  Start config server, then registry, then service, then UI.  Start zipkin anytime.

All the servers come up on default ports.  The service is 8080 and the ui is 8081.

cna-config application.yml needs to be modified to point to your git repo.  I use a repo on my local filesystem (app-config directory).  Do a git init to use it.  Or, point to remote repo.

Apps can come up in any order except for cna-config which needs to come up first (so the rest of the apps can get config from it.)

To use on PCF create services scs-registry, scs-config, and hystrix-dashboard and push the apps.  If you use other service names, change the names in the manifest.yml's.

To use Zipkin on PCF just deploy the app with cf push.  If you change the zipkin server hostname name, make sure to update the manifest.yml's in cna-service and cna-ui.

##Notable changes to make the projects PCF/SCS-Services friendly

Change dependencies from OSS libs to spring-cloud-services-starter-*.  (Using the PCF versions from the initializer does this for you.)

```
		<dependency>
			<groupId>io.pivotal.spring.cloud</groupId>
			<artifactId>spring-cloud-services-starter-service-registry</artifactId>
		</dependency>
		<dependency>
			<groupId>io.pivotal.spring.cloud</groupId>
			<artifactId>spring-cloud-services-starter-config-client</artifactId>
		</dependency>
		<dependency>
			<groupId>io.pivotal.spring.cloud</groupId>
			<artifactId>spring-cloud-services-starter-circuit-breaker</artifactId>
		</dependency>
```

The SCS starters turn on Spring Security, so security is disabled in properties for both the app and actuator.

```
security:
  basic:
    enabled: false
management:
  security:
    enabled: false
```

The RestTemplate needs to be the fancy one.  Create with @LoadBalanced.

```
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
```

I removed (or commented out) references to SCS services in bootstrap.yml.  If something like this needs to be done so you can talk to local services that aren't on standard ports or are on anothe host, then I recommend using a local profile (application-local.yml or equivalent) to set the props.  This way there is no hard coding of PCF service names in property files.  EG: the way config-service is hard coded here.

```
spring:
#  cloud:
#    config:
#      uri: ${vcap.services.config-service.credentials.uri:http://localhost:8888}
```


##old links

The Cloud Native application demos in Spring are divided into sections.  Each section builds on the previous.

Demo 1: [Greeting Micro-Service](demo1.md)

Simple Spring Boot application with a ReST endpoint.  Uses config server.

Demo 2: [Front end application](demo2.md)

Single page application to consume the Greeting service.  Adds service registry, proxy, and circuit breaker.

Demo 3: [Simple Data Micro-service](demo3.md)

Create a simple data micro-service, i.e. one that is triggered asynchronously via a "message" system.
