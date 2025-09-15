package com.abdelaziz26.GivEat.Core.Entities;

import com.abdelaziz26.GivEat.Core.Enums.MatchingStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matchings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double matchingScore;

    @ManyToOne
    @JoinColumn(name = "food_request_id")
    private FoodRequest foodRequest;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus = MatchingStatus.IGNORED;

    @ManyToOne
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

}
