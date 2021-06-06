package com.udacity.jdnd.course3.critter.data;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    private PetType type;
    private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne
    private Customer customer;
}
