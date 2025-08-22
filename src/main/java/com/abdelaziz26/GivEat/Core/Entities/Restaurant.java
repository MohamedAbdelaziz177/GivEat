package com.abdelaziz26.GivEat.Core.Entities;

import jakarta.persistence.*;
import lombok.*;

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


}
