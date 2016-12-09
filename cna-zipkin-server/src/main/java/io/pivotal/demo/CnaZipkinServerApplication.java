package io.pivotal.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class CnaZipkinServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnaZipkinServerApplication.class, args);
	}
}
