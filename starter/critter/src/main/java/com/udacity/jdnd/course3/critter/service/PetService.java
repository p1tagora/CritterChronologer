package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public Pet getPet(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public Pet savePet(Pet pet){
        return petRepository.save(pet);
    }

    public List<Pet> getPets() {
        return Lists.newArrayList(petRepository.findAll());
    }

    public List<Pet> getPetsByOwner(Long owner_id) {
        return Lists.newArrayList(petRepository.findAllByCustomerId(owner_id));
    }
}
