package com.abdelaziz26.GivEat.Core.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "charities")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Charity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Phone is Required")
    private String contactNumber;

    private String description;

    @NotBlank
    private String address;

    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "user-id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "charity")
    private List<FoodRequest> requests;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "charity")
    private List<Donation> donations;

}
