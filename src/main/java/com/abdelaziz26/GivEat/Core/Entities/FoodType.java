package com.abdelaziz26.GivEat.Core.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "food-types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name is required")
    private String name;

    @ManyToMany(mappedBy = "foodTypes")
    private List<Restaurant> restaurants;
}
