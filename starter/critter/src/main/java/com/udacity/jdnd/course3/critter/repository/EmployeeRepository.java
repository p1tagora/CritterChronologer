package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Query("select e from Employee e where :day in elements(e.daysAvailable)")
    List<Employee> findEmployeesByAvailability(DayOfWeek day);
}
