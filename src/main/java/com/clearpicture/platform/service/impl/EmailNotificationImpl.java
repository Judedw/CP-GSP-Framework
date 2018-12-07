package com.clearpicture.platform.service.impl;


import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.dto.model.NotificationContent;
import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * created by Raveen -  18/12/07
 * Domain class for assign notification content for notification service - Initiate from JA-11
 * Advanced invitees assigning feature - JA-11
 */
@Service
public class EmailNotificationImpl implements NotificationService {


    @Autowired
    private PlatformConfigProperties configs;

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationImpl.class);


    public Properties getEmailProperties() {
        Properties mailProps = System.getProperties();


        String emailPort = configs.getGmailSetting().getPort();
        String authEnabled = configs.getGmailSetting().getAuthEnable();
        String tlsEnable = configs.getGmailSetting().getTlsEnable();

        log.info("...........PORT............" + emailPort);
        log.info("...........AUTH............" + authEnabled);
        log.info("...........TLS............" + tlsEnable);

        mailProps.put("mail.smtp.port", emailPort);
        mailProps.put("mail.smtp.auth", authEnabled);
        mailProps.put("mail.smtp.starttls.enable", tlsEnable);

        return mailProps;
    }


    public boolean createAndSendEmail(Properties mailProperties, Session mailSession, NotificationContent notificationContent) {

        String recipients[] = notificationContent.getRecipients();
        String subject = notificationContent.getSubject();
        String body = notificationContent.getBodyContent();
        String emailHost = configs.getGmailSetting().getHost();
        String sender = configs.getGmailSetting().getSenderMail();
        String senderPassword = configs.getGmailSetting().getSenderPassword();


        log.info("...........recipients[]............" + recipients.toString());
        log.info("...........subject............" + subject);
        log.info("...........body............" + body);
        log.info("...........emailHost............" + emailHost);
        log.info("...........sender............" + sender);
        log.info("...........senderPassword............" + senderPassword);


        MimeMessage emailMessage = new MimeMessage(mailSession);

        boolean successStatus = false;

        try {

            for (String recipient : recipients) {
                emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }

            emailMessage.setSubject(subject);
            emailMessage.setContent(body, "text/html");
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(emailHost, sender, senderPassword);
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            transport.close();
            successStatus = true;

        } catch (AddressException addrEx) {
            log.error(".......ERROR IN EMAIL NOTIFICATION SERVICE : ADDRESS SETTING..........");
            addrEx.printStackTrace();
            throw new ComplexValidationException("emailService", "addressSettingError");
        } catch (MessagingException msgEx) {
            log.error(".......ERROR IN EMAIL NOTIFICATION SERVICE : MESSAGE EXCEPTION..........");
            msgEx.printStackTrace();
            throw new ComplexValidationException("emailService", "messageWentWrong.");
        }

        return successStatus;
    }


    @Override
    public boolean sendNotification(NotificationContent notificationContent) {

        Properties properties = getEmailProperties();
        Session mailSession = Session.getDefaultInstance(properties, null);
        boolean result = createAndSendEmail(properties, mailSession, notificationContent);
        return result;
    }
}
