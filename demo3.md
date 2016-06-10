#Create a Data Micro-service

Create an application that is a "data micro-service" and link it together with Spring Cloud
Data Flow.

##1 Start/Create the SCDF Components

The easiest way to get the DataFlow components is to download or build them.  If you need a SCDF server/shell
but don't have access to the built jars, or don't want to build the whole SCDF project, you can create them 
by generating simple Spring Boot apps and adding the appropriate annotations to make each app act appropriately.

1. Create df-server project: Local DataFlow Server
2. Add @EnableDataFlowServer
3. Create df-shell project: Data Flow Shell
4. Add @EnableDataFlowShell

## Create a Data Service

1. Create df-logging-sink: Kafka Stream
2. Add @EnableBinding(Sink.class)
3. Add code endpoing

```
  @MessageEndpoint
  public static class LoggingMessageEndpoint {

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void logIncomingMessages(
        @Payload String msg,
        @Headers Map<String, Object> headers) {

      System.out.println(msg);
      headers.entrySet().forEach(e -> System.out.println(e.getKey() + '=' + e.getValue()));

    }
  }
  ```

Build and install the application in the local maven repository.

```
mvn clean insatll
```

Create a Stream in DataFlow that uses your application.

1. Register the module
2. Create the Stream
3. Deploy it!

```
module register --name custom-log --type sink --uri maven://io.pivotal.pa:df-logging-sink:jar:0.0.1-SNAPSHOT
stream create --name time-to-log --definition 'time | custom-log'
stream deploy --name time-to-log
```
