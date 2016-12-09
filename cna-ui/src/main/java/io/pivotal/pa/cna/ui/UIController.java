package io.pivotal.pa.cna.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UIController {

	GreetingServiceProxy greetingProxy;

	public UIController(GreetingServiceProxy greetingProxy) {
		this.greetingProxy = greetingProxy;
	}

	@GetMapping("/message")
	public Greeting message() {
		return greetingProxy.getMessage();
	}
}
