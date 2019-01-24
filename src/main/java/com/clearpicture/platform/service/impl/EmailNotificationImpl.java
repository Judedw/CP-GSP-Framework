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


import java.util.Date;
import java.util.List;
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


//


    @Override
    public boolean sendNotification(List<NotificationContent> notificationContents) {

        Properties properties = getEmailProperties();
        Session mailSession = Session.getDefaultInstance(properties, null);
        boolean result = createAndSendEmail(properties, mailSession, notificationContents);
        return result;
    }



    public boolean createAndSendEmail(Properties mailProperties, Session mailSession, List<NotificationContent> contentList) {


        boolean successStatus = false;
        try {


            String emailHost = configs.getGmailSetting().getHost();
            String sender = configs.getGmailSetting().getSenderMail();
            String senderPassword = configs.getGmailSetting().getSenderPassword();

            log.info("...........emailHost............" + emailHost);
            log.info("...........sender............" + sender);
            log.info("...........senderPassword............" + senderPassword);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(emailHost, sender, senderPassword);

            contentList.forEach(content -> {
                MimeMessage emailMessage = new MimeMessage(mailSession);
                String recipient = content.getRecipient();
                String subject = content.getSubject();
                String bodyContent = content.getBodyContent();

                try {
                    emailMessage.setFrom(new InternetAddress(sender));
                    emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                    emailMessage.setSubject(subject);
                    emailMessage.setContent(bodyContent, "text/html");
                    emailMessage.setSentDate(new Date());

                    transport.sendMessage(emailMessage, emailMessage.getAllRecipients());

                } catch (AddressException addrEx) {
                    log.error(".......ERROR IN EMAIL NOTIFICATION SERVICE : ADDRESS SETTING..........");
                    addrEx.printStackTrace();
                    throw new ComplexValidationException("emailService", "createAndSendEmail.addressSettingError");
                } catch (MessagingException msgEx) {
                    log.error(".......ERROR IN EMAIL NOTIFICATION SERVICE : MESSAGE EXCEPTION..........");
                    msgEx.printStackTrace();
                    throw new ComplexValidationException("emailService", "createAndSendEmail.messageWentWrong.");
                }
            });

            transport.close();
            successStatus = true;

        } catch (Exception ex) {
            log.error(".......ERROR IN EMAIL NOTIFICATION SERVICE : Transport Setting..........");
            ex.printStackTrace();
            throw new ComplexValidationException("emailService", "createAndSendEmail.transportSetting.");
        }

        return successStatus;
    }



    @Override
    public boolean sendSingleNotification(NotificationContent notificationContent) {
        Properties properties = getEmailProperties();
        Session mailSession = Session.getDefaultInstance(properties, null);
        boolean result = createAndSendSingleEmail(properties, mailSession, notificationContent);
        return result;
    }

    public boolean createAndSendSingleEmail(Properties mailProperties, Session mailSession, NotificationContent content) {
        boolean successStatus = false;
        try {

            String emailHost = configs.getGmailSetting().getHost();
            String sender = configs.getGmailSetting().getSenderMail();
            String senderPassword = configs.getGmailSetting().getSenderPassword();

            log.info("...........emailHost............" + emailHost);
            log.info("...........sender............" + sender);
            log.info("...........senderPassword............" + senderPassword);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(emailHost, sender, senderPassword);

                MimeMessage emailMessage = new MimeMessage(mailSession);
                String recipient = content.getRecipient();
                String subject = content.getSubject();
                String bodyContent = content.getBodyContent();

                try {
                    emailMessage.setFrom(new InternetAddress(sender));
                    emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                    emailMessage.setSubject(subject);
                    emailMessage.setContent(bodyContent, "text/html");
                    emailMessage.setSentDate(new Date());

                    transport.sendMessage(emailMessage, emailMessage.getAllRecipients());

                } catch (AddressException addrEx) {
                    successStatus = false;
                    log.error(".......ERROR IN EMAIL NOTIFICATION SERVICE : ADDRESS SETTING..........");
                    addrEx.printStackTrace();
                    throw new ComplexValidationException("emailService", "createAndSendEmail.addressSettingError");
                } catch (MessagingException msgEx) {
                    successStatus = false;
                    log.error(".......ERROR IN EMAIL NOTIFICATION SERVICE : MESSAGE EXCEPTION..........");
                    msgEx.printStackTrace();
                    throw new ComplexValidationException("emailService", "createAndSendEmail.messageWentWrong.");
                }


            transport.close();
            successStatus = true;

        } catch (Exception ex) {
            successStatus = false;
            log.error(".......ERROR IN EMAIL NOTIFICATION SERVICE : Transport Setting..........");
            ex.printStackTrace();
            throw new ComplexValidationException("emailService", "createAndSendEmail.transportSetting.");
        }

        return successStatus;
    }

// OLD VERSION OF EMAIL SENDERS
//    public boolean createAndSendEmail(Properties mailProperties, Session mailSession, NotificationContent notificationContent) {
//
//
//        String recipients = notificationContent.getRecipient();
//        String subject = notificationContent.getSubject();
//        String body = notificationContent.getBodyContent();
//        String emailHost = configs.getGmailSetting().getHost();
//        String sender = configs.getGmailSetting().getSenderMail();
//        String senderPassword = configs.getGmailSetting().getSenderPassword();
//
//
//        log.info("...........recipients[]............" + recipients.toString());
//        log.info("...........subject............" + subject);
//        log.info("...........body............" + body);
//        log.info("...........emailHost............" + emailHost);
//        log.info("...........sender............" + sender);
//        log.info("...........senderPassword............" + senderPassword);
//
//
//        MimeMessage emailMessage = new MimeMessage(mailSession);
//
//        boolean successStatus = false;
//
//        try {
//
//            for (String recipient : recipients) {
//                emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
//            }
//
//            emailMessage.setSubject(subject);
//            emailMessage.setContent(body, "text/html");
//            Transport transport = mailSession.getTransport("smtp");
//            transport.connect(emailHost, sender, senderPassword);
//            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
//            transport.close();
//            successStatus = true;
//
//        } catch (AddressException addrEx) {
//            log.error(".......ERROR IN EMAIL NOTIFICATION SERVICE : ADDRESS SETTING..........");
//            addrEx.printStackTrace();
//            throw new ComplexValidationException("emailService", "addressSettingError");
//        } catch (MessagingException msgEx) {
//            log.error(".......ERROR IN EMAIL NOTIFICATION SERVICE : MESSAGE EXCEPTION..........");
//            msgEx.printStackTrace();
//            throw new ComplexValidationException("emailService", "messageWentWrong.");
//        }
//
//        return successStatus;
//    }


}
