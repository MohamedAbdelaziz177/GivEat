package com.abdelaziz26.GivEat.Core.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "charities")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Charity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
