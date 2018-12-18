package com.clearpicture.platform.service;


import com.clearpicture.platform.dto.model.NotificationContent;

import java.util.List;

/**
 * created by Raveen -  18/12/07
 * Domain class for assign notification content for notification service - Initiate from JA-11
 * Advanced invitees assigning feature - JA-11
 *
 */
public interface NotificationService {

    public boolean sendNotification(List<NotificationContent> notificationContents);


}
