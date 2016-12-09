package io.pivotal.pa.cna.service.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.pa.cna.service.dom.Greeting;

@RestController
public class CNAServiceController {

	private final Greeting theGreeting;
	
	public CNAServiceController(Greeting theGreeting) {
		this.theGreeting = theGreeting;
	}

	@GetMapping("/greeting")
	public Greeting greeting() {
		return theGreeting;
	}
}
