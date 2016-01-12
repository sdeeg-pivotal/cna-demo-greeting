package io.pivotal.pa.cna.service.dom;

import org.springframework.beans.factory.annotation.Value;

public class Greeting {

	@Value("${app.greeting:doh!}")
	public String greeting = "default";
}
