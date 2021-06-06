package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElse(null);
    }

    public List<Schedule> getAllSchedules() {
        return Lists.newArrayList(scheduleRepository.findAll());
    }

    public Schedule saveSchedule(Schedule schedule) {
        if (schedule.getPets() == null) {
            schedule.setPets(new ArrayList<>());
        }
        if (schedule.getEmployees() == null) {
            schedule.setEmployees(new ArrayList<>());
        }
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedulesByPet(Long petId) {
        Pet pet = petRepository.findById(petId).orElse(null);
        return scheduleRepository.findSchedulesByPet(pet);
    }

    public List<Schedule> getSchedulesByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        return scheduleRepository.findSchedulesByEmployee(employee);
    }

    public List<Schedule> getSchedulesByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        List<Pet> pets = customer.getPets();
        return scheduleRepository.findSchedulesByPets(pets);
    }
}
