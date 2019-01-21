package com.clearpicture.platform.service;


import com.clearpicture.platform.dto.EmailMessage;
import com.clearpicture.platform.dto.EmailWithAttachment;

/**
 * 
 * @author Nuwan
 *
 */
public interface EmailService {

	public boolean sendEmail(EmailMessage message) throws Exception;

	/**
	 * 
	 * @param message
	 */
	public void sendEmailWithAttachment(EmailWithAttachment message);

}
