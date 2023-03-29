package com.example.apidoc.endpoint;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springdoc.core.AbstractSwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apidoc.config.ApidocsConfig;
import com.example.apidoc.dto.ServiceTarget;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApidocEndpoint {
	
	private ApidocsConfig config;
	private List<ServiceTarget> serviceList = null;
	
	@Autowired
	public void setConfig(ApidocsConfig config) {
		this.config = config;
	}

    
	@GetMapping("/v3/api-docs/swagger-config")
    public Map<String, Object> swaggerConfig(ServerHttpRequest serverHttpRequest) throws URISyntaxException {
        URI uri = serverHttpRequest.getURI();
        
        String url = new URI(uri.getScheme(), uri.getAuthority(), null, null, null).toString();

        Map<String, Object> swaggerConfig = new LinkedHashMap<>();
        List<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls = new LinkedList<>();
        
        serviceList = config.getTarget();
        for(int i=0; i<serviceList.size(); i++) {
        	log.info("APIDOC Service:[{}], Context-path:[{}]", serviceList.get(i).getName(), serviceList.get(i).getContextPath());
        	swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl(serviceList.get(i).getName(), url+"/"+serviceList.get(i).getContextPath()+"/v3/api-docs"));
        }
                
        swaggerConfig.put("urls", swaggerUrls);        
        log.info("APIDOC swagger urls:{}", swaggerConfig);
        
        return swaggerConfig;
    }
    
}
