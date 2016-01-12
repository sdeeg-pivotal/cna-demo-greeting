package io.pivotal.pa.cna.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.pa.cna.service.dom.Greeting;

@RestController
public class CNAServiceController {

	@Autowired
	Greeting theGreeting;
	
	@RequestMapping("/greeting")
	public Greeting greeting() {
		return theGreeting;
	}
}
