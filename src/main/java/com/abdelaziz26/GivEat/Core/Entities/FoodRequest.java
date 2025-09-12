package com.abdelaziz26.GivEat.Core.Entities;

import com.abdelaziz26.GivEat.Core.Enums.FoodCategory;
import com.abdelaziz26.GivEat.Core.Enums.FoodCondition;
import com.abdelaziz26.GivEat.Core.Enums.FoodRequestStatus;
import com.abdelaziz26.GivEat.Core.Enums.QuantityUnit;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "food-requests")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"charity", "matchings"})
public class FoodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime requestDate = LocalDateTime.now();

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Positive
    private double quantity;

    @Enumerated(EnumType.STRING)
    private QuantityUnit unit;

    @Nullable
    private LocalDateTime expiryLimit;

    @Enumerated(EnumType.STRING)
    private FoodCondition condition;

    @Enumerated(EnumType.STRING)
    private FoodRequestStatus status;

    @Enumerated(EnumType.STRING)
    private FoodCategory foodCategory;

    @Range(min = 1, max = 5)
    private int urgency;

    private boolean requiresHalal;

    private boolean requiresKosher;

    private boolean vegetarianOnly;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "charity_id")
    private Charity charity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "foodRequest")
    private List<Matching> matchings;
}
