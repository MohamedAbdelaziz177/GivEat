package com.abdelaziz26.GivEat.Core.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Phone is Required")
    private String contactNumber;

    private String description;

    private String address;

    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "user-id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "restaurant")
    private List<FoodItem> foodItems;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
    private List<Donation> donations;

    @ManyToMany
    @JoinTable(
            name = "restaurant_food-type",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<FoodType> foodTypes;


}
