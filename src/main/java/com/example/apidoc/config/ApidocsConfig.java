package com.example.apidoc.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.example.apidoc.dto.ServiceTarget;

@Component
@ConfigurationProperties("services")
public class ApidocsConfig {

	private List<ServiceTarget> ServiceTarget = new ArrayList<>();
	
	public List<ServiceTarget> getTarget() {
		return ServiceTarget;
	}
	
	
}
