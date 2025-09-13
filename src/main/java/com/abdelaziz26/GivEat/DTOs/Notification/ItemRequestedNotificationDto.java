package com.abdelaziz26.GivEat.DTOs.Notification;

import com.abdelaziz26.GivEat.DTOs.Matching.FoodItemMatchedDto;
import com.abdelaziz26.GivEat.DTOs.Matching.MatchingResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequestedNotificationDto extends NotificationDto {

    private Long foodItemId;
    private Long matchingId;
    private Long requestId;
}
