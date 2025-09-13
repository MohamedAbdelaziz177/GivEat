package com.abdelaziz26.GivEat.Core.Interfaces;

import com.abdelaziz26.GivEat.Core.Entities.Notification;
import com.abdelaziz26.GivEat.Core.Enums.NotificationType;
import com.abdelaziz26.GivEat.DTOs.Notification.ItemRequestedNotificationDto;
import com.abdelaziz26.GivEat.DTOs.Notification.RequestResponseNotificationDto;

public interface NotificationService {
    Notification saveNotification(NotificationType type, Long matchId);

    ItemRequestedNotificationDto getNotification(Notification notification);

    RequestResponseNotificationDto getRequestResponseNotification(Notification notification);
}
