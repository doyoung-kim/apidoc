package com.example.apidoc.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/system")
@RefreshScope
@RequiredArgsConstructor
public class SystemEndpoint {

	@Value(value = "${spring.application.name}")
	String appName;
	
	@GetMapping("/healthz")
	public String getSystemHealth() {
		return "["+ appName + "] Service is Healthy";
	}
	
}
	