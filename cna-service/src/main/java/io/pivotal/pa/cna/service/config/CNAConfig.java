package io.pivotal.pa.cna.service.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.pivotal.pa.cna.service.dom.Greeting;

@Configuration
@EnableDiscoveryClient
public class CNAConfig {

	@Bean
	public Greeting newGreeting() { return new Greeting(); }
}
