package com.rma.demov2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class Demov2Application {
	@Value("${api.common.version}")
	String apiVersion;
	@Value("${api.common.title}")
	String apiTitle;
	@Value("${api.common.description}")
	String apiDescription;
	@Value("${api.common.license}")
	String apiLicense;

	public static void main(String[] args) {
		SpringApplication.run(Demov2Application.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title(apiTitle)
						.description(apiDescription)
						.version(apiVersion)
						.license(new License()
								.name(apiLicense)));

	}
}
