package com.abdelaziz26.GivEat.Core.Entities;

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

    private double quantity;

    private LocalDateTime createdAt;

    private LocalDateTime expiryDate;

    private String description;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "foodItem")
    private List<FoodRequest> requests;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "foodItem")
    private List<Matching> matchings;
}
