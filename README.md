#CNA Demo Notes

The instructions in the demo script pags are out of date with the code.  All the projects have been updated
to consume Spring Cloud Services hosted on PCF.  Sleuth/Zipkin has also been added.

All the servers come up on default ports.  The service is 8080 and the ui is 8081.

cna-config application.yml needs to be modified to point to your git repo.  I use a repo on my local filesystem (app-config directory).  Do a git init to use it.  Or, point to remote repo.

Apps can come up in any order except for cna-config which needs to come up first (so the rest of the apps can get config from it.)

To use on PCF create services scs-registry, scs-config, and hystrix-dashboard and push the apps.  If you use other service names, change the names in the manifest.yml's.

To use Zipkin on PCF just deploy the app with cf push.  If you change the zipkin server hostname name, make sure to update the manifest.yml's in cna-service and cna-ui.

(old stuff)

The Cloud Native application demos in Spring are divided into sections.  Each section builds on the previous.

Demo 1: [Greeting Micro-Service](demo1.md)

Simple Spring Boot application with a ReST endpoint.  Uses config server.

Demo 2: [Front end application](demo2.md)

Single page application to consume the Greeting service.  Adds service registry, proxy, and circuit breaker.

Demo 3: [Simple Data Micro-service](demo3.md)

Create a simple data micro-service, i.e. one that is triggered asynchronously via a "message" system.
