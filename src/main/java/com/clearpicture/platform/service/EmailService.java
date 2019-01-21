package com.clearpicture.platform.service;


import com.clearpicture.platform.dto.EmailMessage;
import com.clearpicture.platform.dto.EmailWithAttachment;

/**
 * 
 * @author Virajith
 *
 */
public interface EmailService {

	public void sendEmail(EmailMessage message);

	/**
	 * 
	 * @param message
	 */
	public void sendEmailWithAttachment(EmailWithAttachment message);

}
