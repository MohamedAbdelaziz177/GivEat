package com.abdelaziz26.GivEat.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @RequestMapping("/mine")
    public String getNotifications() {
       return "TO BE IMPLEMENTED";
    }
}
