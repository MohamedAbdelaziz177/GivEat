package com.abdelaziz26.GivEat.Core.Entities;

import com.abdelaziz26.GivEat.Core.Enums.FoodCondition;
import com.abdelaziz26.GivEat.Core.Enums.QuantityUnit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "food-items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private double quantity;

    @Enumerated(EnumType.STRING)
    private QuantityUnit unit;

    @Enumerated(EnumType.STRING)
    private FoodCondition condition;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "foodItem")
    private List<FoodRequest> requests;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "foodItem")
    private List<Matching> matchings;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "foodItem")
    private List<FoodItemImage> images;
}
