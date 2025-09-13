package com.abdelaziz26.GivEat.DTOs.Notification;

import com.abdelaziz26.GivEat.Core.Enums.FoodRequestStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestResponseNotificationDto extends NotificationDto {

    private Long foodItemId;

    private Long matchId;

    private String status;
}
