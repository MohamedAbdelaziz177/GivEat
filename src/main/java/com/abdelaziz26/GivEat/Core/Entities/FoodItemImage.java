package com.abdelaziz26.GivEat.Core.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food-item-images")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FoodItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "food-item-id")
    private FoodItem foodItem;
}
