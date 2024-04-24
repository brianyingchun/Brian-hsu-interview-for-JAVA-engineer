package com.coindesk.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages = {"com.coindesk"})
@EnableJpaRepositories(basePackages = "com.coindesk.entity")
@EntityScan(basePackages = "com.coindesk.entity")
public class TestApplication{
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
	

}
