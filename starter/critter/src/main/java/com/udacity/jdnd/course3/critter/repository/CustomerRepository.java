package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.data.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Query("select c from Customer c where :petId in elements(c.pets)")
    Customer findCustomerByPetId(Long petId);
}
