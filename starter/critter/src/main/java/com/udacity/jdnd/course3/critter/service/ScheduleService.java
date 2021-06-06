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
        return scheduleRepository.findById(scheduleId).orElseThrow(ScheduleNotFoundException::new);
    }

    public List<Schedule> getAllSchedules() {
        return Lists.newArrayList(scheduleRepository.findAll());
    }

    public Schedule saveSchedule(Schedule schedule) {
        if (schedule.getPets() == null
                || schedule.getEmployees() == null
                || schedule.getDate() == null
                || schedule.getActivities() == null) {
            throw new InvalidNewScheduleException();
        }
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedulesByPet(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
        return scheduleRepository.findSchedulesByPet(pet);
    }

    public List<Schedule> getSchedulesByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        return scheduleRepository.findSchedulesByEmployee(employee);
    }

    public List<Schedule> getSchedulesByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        List<Pet> pets = customer.getPets();
        List<Schedule> schedules = new ArrayList<>();
        for (Pet p : pets) {
            schedules.addAll(scheduleRepository.findSchedulesByPet(p));
        }
        return schedules;
    }
}
