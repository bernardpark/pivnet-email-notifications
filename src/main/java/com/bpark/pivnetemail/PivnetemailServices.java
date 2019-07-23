package com.bpark.pivnetemail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bpark.pivnetemail.service.PivnetService;

@Configuration
public class PivnetemailServices {

	@Bean
	public PivnetService pivnetService() {
		final PivnetService pivnetService = new PivnetService();
		return pivnetService;
	}



}
