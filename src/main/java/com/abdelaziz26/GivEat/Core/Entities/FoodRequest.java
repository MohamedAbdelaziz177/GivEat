package com.abdelaziz26.GivEat.Core.Entities;

import com.abdelaziz26.GivEat.Core.Enums.FoodCondition;
import com.abdelaziz26.GivEat.Core.Enums.FoodRequestStatus;
import com.abdelaziz26.GivEat.Core.Enums.QuantityUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "requests")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FoodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime requestDate = LocalDateTime.now();

    @Column(nullable = false)
    @NotBlank
    private String name;

    private double quantity;

    @Enumerated(EnumType.STRING)
    private QuantityUnit unit;

    private LocalDateTime expiryLimit;

    @Enumerated(EnumType.STRING)
    private FoodCondition condition;

    @Enumerated(EnumType.STRING)
    private FoodRequestStatus status;

    @ManyToOne
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "charity_id")
    private Charity charity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "foodRequest")
    private List<Matching> matchings;
}
