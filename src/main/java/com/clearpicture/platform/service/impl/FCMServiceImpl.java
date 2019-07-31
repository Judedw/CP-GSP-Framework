package com.clearpicture.platform.service.impl;

import com.clearpicture.platform.dto.model.PushNotificationContent;
import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.service.FCMService;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.util.Map;

import java.util.logging.Logger;


@Service
public class FCMServiceImpl implements FCMService {

    Logger LOGGER = Logger.getLogger(FCMServiceImpl.class.getName());


    @Override
    public void sendMessageToTopic(PushNotificationContent content) {
        Message message = getPreconfiguredMessageToTopic(content);
        String response = sendAsyncMessage(message);
        LOGGER.info("sendMessageToTopic:::::::::::::::" + response);
    }

    @Override
    public void sendMessageToToken(PushNotificationContent content) {
        Message message = getPreconfiguredMessageToToken(content);
        String response = sendMessage(message);
        LOGGER.info("sendMessageToToken:::::::::::::::" + response);
    }


    private Message getPreconfiguredMessageToToken(PushNotificationContent content) {
        Message.Builder builder = getPreConfigMessageBuilder(content);
        return builder.setToken(content.getToken()).build();
    }

    private Message getPreconfiguredMessageToTopic(PushNotificationContent content) {
        Message.Builder builder = getPreConfigMessageBuilder(content);
        return builder.setTopic(content.getTopic()).build();
    }

    private Message.Builder getPreConfigMessageBuilder(PushNotificationContent content) {
        String topicValue = content.getTopic();
        ApnsConfig apnsConfig = getApnsConfig(topicValue == null ? "" : topicValue);
        Message.Builder builder = Message.builder().setApnsConfig(apnsConfig).setNotification(new Notification(content.getTitle(), content.getMessage()));

        // If Additional Data present ,
        Map<String, String> data = content.getData();
        if (data != null) {
            builder.putAllData(data);
        }
        return builder;
    }

    private String sendAsyncMessage(Message message) {

        try {
            return FirebaseMessaging.getInstance().sendAsync(message).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComplexValidationException("fcm", "sendAsyncMessageError");
        }
    }

    private String sendMessage(Message message) {
        try {
            return FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComplexValidationException("fcm", "sendMessageError");
        }

    }


    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }
}
