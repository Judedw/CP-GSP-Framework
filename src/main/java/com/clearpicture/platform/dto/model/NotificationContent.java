package com.clearpicture.platform.dto.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Raveen -  18/12/07
 * Domain class for assign notification content for notification service - Initiate from JA-11
 * Advanced invitees assigning feature - JA-11
 */
@Data
public class NotificationContent {

    private String sender;
    private String subject;
    private String bodyContent;
    private String recipient;


    public NotificationContent(String sender, String subject, String bodyContent, String recipient) {
        this.sender = sender;
        this.subject = subject;
        this.bodyContent = bodyContent;
        this.recipient = recipient;
    }


    public static class NotificationContentBuilder {
        private String sender;
        private String subject;
        private String bodyContent;
        private String recipient;


        public NotificationContentBuilder setSender(String sender) {
            this.sender = sender;
            return this;
        }

        public NotificationContentBuilder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public NotificationContentBuilder setBodyContent(String bodyContent) {
            this.bodyContent = bodyContent;
            return this;
        }

        public NotificationContentBuilder setRecipients(String[] recipients) {
            this.recipient = recipient;
            return this;
        }

        public NotificationContent createNotificationContent() {
            return new NotificationContent(sender, subject, bodyContent, recipient);
        }


    }


}
