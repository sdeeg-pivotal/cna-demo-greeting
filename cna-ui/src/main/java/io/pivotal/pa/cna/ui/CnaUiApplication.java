package io.pivotal.pa.cna.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class CnaUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnaUiApplication.class, args);
	}
}
