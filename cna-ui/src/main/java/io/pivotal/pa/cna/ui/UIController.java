package io.pivotal.pa.cna.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class UIController {
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
}
