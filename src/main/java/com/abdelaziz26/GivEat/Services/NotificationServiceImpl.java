package com.abdelaziz26.GivEat.Services;

import com.abdelaziz26.GivEat.Core.Entities.Matching;
import com.abdelaziz26.GivEat.Core.Entities.Notification;
import com.abdelaziz26.GivEat.Core.Enums.NotificationType;
import com.abdelaziz26.GivEat.Core.Interfaces.NotificationService;

import com.abdelaziz26.GivEat.Core.MagicValues;
import com.abdelaziz26.GivEat.Core.Repositories.MatchingRepository;
import com.abdelaziz26.GivEat.Core.Repositories.NotificationRepository;
import com.abdelaziz26.GivEat.DTOs.Notification.ItemRequestedNotificationDto;
import com.abdelaziz26.GivEat.DTOs.Notification.RequestResponseNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final MatchingRepository matchingRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public Notification saveNotification(NotificationType type, Long matchId) {

        var matching = matchingRepository.findById(matchId).orElseThrow(() ->
                new IllegalArgumentException("Matching not found")
        );

        var notification = new Notification();
        notification.setMessage("message");
        notification.setMatching(matching);
        notification.setType(type);

        return notificationRepository.save(notification);
    }

    /*  need to be refactored -_- */

    // webSocket listener instead of polling

    @Override
    public ItemRequestedNotificationDto getNotification(Notification notification) {

        Matching matching = matchingRepository.findById(notification.getMatching().getId()).orElseThrow();

        return ItemRequestedNotificationDto
                .builder()
                .foodItemId(matching.getFoodItem().getId())
                .matchingId(matching.getId())
                .requestId(matching.getFoodRequest().getId())
                .build();

    }

    // webSocket listener instead of polling

    @Override
    public RequestResponseNotificationDto getRequestResponseNotification(Notification notification) {

        Matching matching = matchingRepository.findById(notification.getMatching().getId()).orElseThrow();

        return RequestResponseNotificationDto
                .builder()
                .foodItemId(matching.getFoodItem().getId())
                .matchId(matching.getId())
                .status(matching.getFoodRequest().getStatus().toString())
                .build();
    }
}
