package com.clearpicture.platform.dto;


import lombok.Data;

import java.io.InputStream;

@Data
public class EmailWithAttachment {

	private String to;

	private String subject;

	private String content;

	private boolean html;

	private String fileName;

	private InputStream dataToBeAttached;
	
	private String fileContentType;
}
