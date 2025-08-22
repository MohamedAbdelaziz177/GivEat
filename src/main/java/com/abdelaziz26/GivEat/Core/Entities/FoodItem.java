package com.abdelaziz26.GivEat.Core.Entities;

import jakarta.persistence.*;
import lombok.*;

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
}
