package com.clearpicture.platform.service.impl;

import com.clearpicture.platform.componant.RestApiClient;
import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.dto.response.GeneralAuthResponse;
import com.clearpicture.platform.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author Nuwan
 *
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private PlatformConfigProperties configProps;

	@Autowired
	private RestApiClient restApiClient;

	/*@Override
	@Cacheable(cacheNames = "ms2msAuthCache", sync = true)
	public String retrieveMs2MsAuthToken() {
		return executeMs2MsAuthService();
	}*/

	/*@Override
	@CachePut(cacheNames = "ms2msAuthCache")
	@Scheduled(initialDelay = 300000, fixedRateString = "#{${app.auth.ms2msAuthTokenRenewFreq}*1000}")
	public String generateMs2MsAuthToken() {
		if (configProps.getAuth().isMs2msAuthEnabled()) {
			return executeMs2MsAuthService();
		} else {
			return null;
		}
	}*/
	
	/*@Override
	public String[] executeMobileAuth(String endpoint, String user, String pass) {
		RestTemplate rt = restApiClient.getRestTemplateForMobileAuth();
		String[] tokens = executeAuth(rt, endpoint, user, pass, false);
		log.info("Mobile auth token API call (B2B) completed!");
		return tokens;
	}*/
	
	@Override
	public String[] executeAdminPortalAuth(String endpoint, String user, String pass) {
		RestTemplate rt = restApiClient.getRestTemplateForAdminPortalAuth();
		String[] tokens = executeAuth(rt, endpoint, user, pass, false);
		log.info("Admin portal auth token API call (B2B) completed!");
		return tokens;
	}
	
	/*@Override
	public String[] executeClientPortalAuth(String endpoint, String user, String pass) {
		RestTemplate rt = restApiClient.getRestTemplateForClientPortalAuth();
		String[] tokens = executeAuth(rt, endpoint, user, pass, true);
		log.info("Client portal auth token API call (B2B) completed!");
		return tokens;
	}*/
	
	private String[] executeAuth(RestTemplate rt, String endpoint, String user, String pass, boolean isClientPortal) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grant_type", "password");
		params.add("username", user);
		params.add("password", pass);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
				headers);
		GeneralAuthResponse response = rt.postForObject(endpoint, request, GeneralAuthResponse.class);
		
		if (isClientPortal) {
			return new String[] { response.getAccess_token(), response.getRefresh_token(), response.getRedirectToOnboarding() };
		} else {
			return new String[] { response.getAccess_token(), response.getRefresh_token() };
		}
	}
	
	/*private String executeMs2MsAuthService() {
		RestTemplate rt = restApiClient.getRestTemplateForTokenGeneration();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grant_type", "client_credentials");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
				headers);

		Ms2MsAuthResponse response = rt.postForObject(configProps.getAuth().getMs2msAuthEndpoint(), request,
				Ms2MsAuthResponse.class);
		log.info("MS2MS auth token API call completed!");

		return response.getAccess_token();
	}*/
}
