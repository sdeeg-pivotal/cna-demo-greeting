#Create a Data Micro-service

Create an application that is a "data micro-service" and link it together with Spring Cloud
Data Flow.

##1 Create the Data Service

1. Create df-server project: Local DataFlow Server
2. Add @EnableDataFlowServer
3. Create df-shell project: Data Flow Shell
4. Add @EnableDataFlowShell
5. Create df-logging-sink: Kafka Stream
6. Add @EnableBinding(Sink.class)
7. Add code endpoing

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

8. Register the module
9. Create the Stream
10. Deploy it!

```
module register --name custom-log --type sink --uri maven://io.pivotal.pa:df-logging-sink:jar:0.0.1-SNAPSHOT
stream create --name time-to-log --definition 'time | custom-log'
stream deploy --name time-to-log
```

