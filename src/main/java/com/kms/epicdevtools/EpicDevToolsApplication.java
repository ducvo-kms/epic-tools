package com.kms.epicdevtools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients()
public class EpicDevToolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpicDevToolsApplication.class, args);
	}


	@Bean
	ObjectMapper objectMapper(){
		return new ObjectMapper();
	}
}
