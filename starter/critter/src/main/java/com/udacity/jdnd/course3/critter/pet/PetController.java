package com.udacity.jdnd.course3.critter.pet;

import com.google.common.collect.Lists;
import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.savePet(convertPetDTOtoPet(petDTO));
        Customer customer = customerService.getCustomer(pet.getCustomer().getId());
        customerService.addPet(pet.getId(), customer.getId());
        return convertPetToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> result = new ArrayList<>();
        for (Pet p : petService.getPets()) {
            result.add(convertPetToPetDTO(p));
        }
        return result;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> result = new ArrayList<>();
        for (Pet p : petService.getPetsByOwner(ownerId)) {
            result.add(convertPetToPetDTO(p));
        }
        return result;
    }

    private Pet convertPetDTOtoPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        Customer customer = customerService.getCustomer(petDTO.getOwnerId());
        /*TODO this should maybe be moved in a different package, as I am breaking the separation of concerns here
        *  by accessing the Pet Entity directly from the controller */
        pet.setCustomer(customer);
        return pet;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }
}
