package com.abdelaziz26.GivEat.Core.Entities;

import com.abdelaziz26.GivEat.Core.Enums.FoodTypeEn;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "food_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FoodTypeEn name;

    @ManyToMany(mappedBy = "foodTypes")
    private List<Restaurant> restaurants;
}
