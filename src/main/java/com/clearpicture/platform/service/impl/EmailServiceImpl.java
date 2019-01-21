package com.clearpicture.platform.service.impl;



import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.mail.simplemail.SimpleEmailServiceJavaMailSender;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.dto.EmailMessage;
import com.clearpicture.platform.dto.EmailWithAttachment;
import com.clearpicture.platform.service.EmailService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @author Nuwan
 *
 */
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private PlatformConfigProperties configProps;

	@Autowired
	private SimpleEmailServiceJavaMailSender mailSender;

	@Override
	public void sendEmail(EmailMessage message) {
		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException {
				MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mmh.setFrom(configProps.getEmail().getFrom(), configProps.getEmail().getFromName());
				mmh.setTo(message.getTo());
				mmh.setSubject(message.getSubject());
				mmh.setText(message.getContent(), message.isHtml());
				// mmh.addInline("inline1", new ClassPathResource("pic.gif"));
				// mmh.addAttachment("doc.pdf", new ClassPathResource("doc.pdf"));
			}
		});
	}

	@Override
	public void sendEmailWithAttachment(EmailWithAttachment message) {
		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException, IOException {
				MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mmh.setFrom(configProps.getEmail().getFrom(), configProps.getEmail().getFromName());
				mmh.setTo(message.getTo());
				mmh.setSubject(message.getSubject());
				mmh.setText(message.getContent(), message.isHtml());
				mmh.addAttachment(message.getFileName(),
						new ByteArrayResource(IOUtils.toByteArray(message.getDataToBeAttached())));
			}
		});

	}


}
