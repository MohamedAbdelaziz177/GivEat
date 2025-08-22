package com.abdelaziz26.GivEat.Core.Entities;

import jakarta.persistence.*;
import lombok.*;

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
}
