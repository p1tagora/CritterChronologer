package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.data.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Employee e set e.daysAvailable = :daysAvailable where e.id = :employeeId")
    void setDaysAvailable(Set<LocalDate> daysAvailable, Long employeeId);
}
