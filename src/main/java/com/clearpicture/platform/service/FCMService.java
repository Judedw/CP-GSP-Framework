package com.clearpicture.platform.service;

import com.clearpicture.platform.dto.model.PushNotificationContent;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * created by Raveen -  2019/07/19
 * <p>
 * FirebaseInitService - Firebase Cloud Messaging Service Initializing
 */

public interface FCMService {


    void sendMessageToTopic(PushNotificationContent content);

    void sendMessageToToken(PushNotificationContent content);


}
