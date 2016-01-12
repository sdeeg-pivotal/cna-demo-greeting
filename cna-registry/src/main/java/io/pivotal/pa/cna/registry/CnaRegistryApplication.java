package io.pivotal.pa.cna.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CnaRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnaRegistryApplication.class, args);
	}
}
