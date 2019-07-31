package com.clearpicture.platform.dto.model;


import lombok.Data;

import java.util.Map;


/**
 * created by Raveen -  2019/07/23
 * <p>
 * PushNotificationContent - Common Service for All the location stuff
 */


@Data
public class PushNotificationContent {


    private String title;


    private String message;
    private String token;
    private String topic;
    private Map<String, String> data;

    public PushNotificationContent(String title, String message, String token, Map<String, String> data) {
        this.title = title;
        this.message = message;
        this.token = token;
        this.data = data;
    }

    public PushNotificationContent(String title, String message, String token, String topic, Map<String, String> data) {
        this.title = title;
        this.message = message;
        this.token = token;
        this.topic = topic;
        this.data = data;
    }

    public PushNotificationContent() {
    }


}
