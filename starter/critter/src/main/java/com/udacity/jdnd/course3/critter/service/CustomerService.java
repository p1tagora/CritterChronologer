package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    public List<Customer> getAllCustomers() {
        return Lists.newArrayList(customerRepository.findAll());
    }

    public Customer saveCustomer(Customer customer) {
        if (customer.getPets() == null) {
            customer.setPets(new ArrayList<>());
        }
        return customerRepository.save(customer);
    }

    public Customer getCustomerByPetId(Long petId){
        return customerRepository.findCustomerByPet(petRepository.findById(petId).orElseThrow(PetNotFoundException::new));
    }

    public Customer addPet(Long petId, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        List<Pet> pets = customer.getPets();
        Pet pet = petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
        pets.add(pet);
        customer.setPets(pets);
        return customerRepository.save(customer);
    }
}
