package io.pivotal.pa.cna.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class GreetingServiceProxy {
	
	Logger log = LoggerFactory.getLogger(GreetingServiceProxy.class);

	private RestTemplate restTemplate;
	private Greeting fallbackMessage;
	private final Tracer tracer;
    
	public GreetingServiceProxy(RestTemplate restTemplate, Greeting fallbackMessage, Tracer tracer) {
		this.restTemplate = restTemplate;
		this.fallbackMessage = fallbackMessage;
		this.tracer = tracer;
	}

	public Greeting messageFallback() {
		return fallbackMessage;
	}

	@HystrixCommand(fallbackMethod = "messageFallback")
	public Greeting getMessage() {
		log.debug("Getting the message");
		
		Greeting response = new Greeting("nothing");
		Span span = null;
		try {
			span = tracer.createSpan("callingBackOfficeMicroService_span");
			span.logEvent("call_backOfficeMicroService");
			response = restTemplate.getForObject("http://cna-service/greeting", Greeting.class);
			span.logEvent("response_received_backOfficeMicroService");
			tracer.addTag("ui","success");
		}finally {
			tracer.close(span);
		}
		log.debug("Got the response: "+response.greeting);
		return response;
	}

}
