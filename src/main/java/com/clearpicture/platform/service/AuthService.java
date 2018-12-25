package com.clearpicture.platform.service;

/**
 * 
 * @author Nuwan
 *
 */
public interface AuthService {

	/*String retrieveMs2MsAuthToken();

	String generateMs2MsAuthToken();

	String[] executeMobileAuth(String endpoint, String user, String pass);*/

	String[] executeAdminPortalAuth(String endpoint, String user, String pass);

	//String[] executeClientPortalAuth(String endpoint, String user, String pass);

}
