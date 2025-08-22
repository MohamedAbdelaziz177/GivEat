package com.abdelaziz26.GivEat.Core.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matchings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
