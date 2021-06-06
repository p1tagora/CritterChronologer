package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    @Query("select s from Schedule s where :pet in elements(s.pets)")
    List<Schedule> findSchedulesByPet(Pet pet);

    @Query("select s from Schedule s where :employee in elements(s.employees)")
    List<Schedule> findSchedulesByEmployee(Employee employee);

    @Query("select s from Schedule s where :pets in elements(s.pets)")
    List<Schedule> findSchedulesByPets(List<Pet> pets);
}
