package com.abdelaziz26.GivEat.Core.Entities;

import com.abdelaziz26.GivEat.Core.Enums.FoodCategory;
import com.abdelaziz26.GivEat.Core.Enums.FoodCondition;
import com.abdelaziz26.GivEat.Core.Enums.FoodItemStatus;
import com.abdelaziz26.GivEat.Core.Enums.QuantityUnit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food-items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double quantity;

    @Enumerated(EnumType.STRING)
    private QuantityUnit unit;

    @Enumerated(EnumType.STRING)
    private FoodCondition condition;

    @Enumerated(EnumType.STRING)
    private FoodCategory foodCategory;

    @Enumerated(EnumType.STRING)
    private FoodItemStatus foodItemStatus;

    private boolean halalCertified;

    private boolean kosherCertified;

    private boolean vegetarianFriendly;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "foodItem")
    private List<Matching> matchings;

    @ElementCollection
    private List<String> imagesUrls;

    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "foodItem")
    //private List<FoodItemImage> images;


   //public FoodItem setImages(List<String> imagesUrls) {
   //    List<FoodItemImage> images = new ArrayList<>();
   //    for (String imageUrl : imagesUrls) {
   //        images.add(FoodItemImage.builder()
   //                .imageUrl(imageUrl)
   //                .foodItem(this)
   //                .build());
   //    }
   //    return this;
   //}
}
