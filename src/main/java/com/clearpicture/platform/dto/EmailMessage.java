package com.clearpicture.platform.dto;

import lombok.Data;

/**
 * 
 * @author Virajith
 *
 */
@Data
public class EmailMessage {

	private String to;

	private String subject;

	private String content;

	private boolean html;

}
