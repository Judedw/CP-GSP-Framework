package com.clearpicture.platform.componant;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.clearpicture.platform.service.impl.ClientDetailsServiceImpl;
import com.clearpicture.platform.util.PlatformConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * @author Nuwan
 *
 */
@Component
public class RestApiClient {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	private RestTemplate restTemplate;

	@Autowired
	private DataSource dataSource;

	public RestApiClient() {}

	@PostConstruct
	public void init()
	{
		restTemplate = restTemplateBuilder.build();
	}

	public RestTemplate getRestTemplate()
	{
		return restTemplate;
	}

	public RestTemplate getRestTemplateForAdminPortalAuth() {
		ClientDetailsService clientDetailsService = new ClientDetailsServiceImpl(dataSource);
		ClientDetails client = clientDetailsService.loadClientByClientId(PlatformConstant.AUTH_CLIENT_CPAP);
		RestTemplate rt = restTemplateBuilder.additionalInterceptors(
				new BasicAuthorizationInterceptor(client.getClientId(), client.getClientSecret())).build();
		return rt;
	}

}
