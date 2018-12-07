package com.clearpicture.platform.service.impl;


import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.dto.model.NotificationContent;
import com.clearpicture.platform.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * created by Raveen -  18/12/07
 * Domain class for assign notification content for notification service - Initiate from JA-11
 * Advanced invitees assigning feature - JA-11
 *
 */
@Service
public class EmailNotificationImpl implements NotificationService {


    @Autowired
    private PlatformConfigProperties configs;


    public Properties getEmailProperties(){
        Properties mailProps = System.getProperties();


        String emailPort = configs.getGmailSetting().getPort();
        String authEnabled =  configs.getGmailSetting().getAuthEnable();
        String tlsEnable =

        mailProps.put("mail.smtp.port",)
        return mailProps;
    }


    @Override
    public boolean sendNotification(NotificationContent notificationContent) {



        return false;
    }
}
