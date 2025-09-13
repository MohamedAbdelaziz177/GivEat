package com.abdelaziz26.GivEat.Core.Entities;

import com.abdelaziz26.GivEat.Core.Enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public  class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private NotificationType type;

    @OneToOne
    @JoinColumn(name = "match-id")
    private Matching matching;
}
