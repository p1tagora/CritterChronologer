package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.apache.tomcat.jni.Local;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public List<Employee> getAllEmployees() {
        return Lists.newArrayList(employeeRepository.findAll());
    }
    
    public Employee setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        employee.setDaysAvailable(daysAvailable);
        return employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        List<Employee> employees =  employeeRepository.findEmployeesByAvailability(DayOfWeek.from(date));
        List<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getSkills().containsAll(skills)) {
                result.add(e);
            }
        }
        return result;
    }
}
