package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertScheduleToScheduleDTO(scheduleService.saveSchedule(convertScheduleDTOtoSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getAllSchedules();
        for (Schedule s : schedules) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(s));
        }
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getSchedulesByPet(petId);
        for (Schedule s : schedules) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(s));
        }
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getSchedulesByEmployee(employeeId);
        for (Schedule s : schedules) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(s));
        }
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getSchedulesByCustomer(customerId);
        for (Schedule s : schedules) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(s));
        }
        return scheduleDTOS;
    }

    private Schedule convertScheduleDTOtoSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        //set petIds
        if (schedule.getPets() == null) {
            List<Long> petIds = scheduleDTO.getPetIds();
            if (petIds != null) {
                List<Pet> pets = new ArrayList<>();
                for (Long petId : petIds) {
                    pets.add(petService.getPet(petId));
                }
                schedule.setPets(pets);
            }
        }
        //set employeeIds
        if (schedule.getEmployees() == null) {
            List<Long> employeeIds = scheduleDTO.getEmployeeIds();
            if (employeeIds != null) {
                List<Employee> employees = new ArrayList<>();
                for (Long employeeId : employeeIds) {
                    employees.add(employeeService.getEmployee(employeeId));
                }
                schedule.setEmployees(employees);
            }
        }
        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        if (scheduleDTO.getPetIds() == null) {
            List<Pet> pets = schedule.getPets();
            List<Long> petIds = new ArrayList<>();
            for (Pet p : pets) {
                petIds.add(p.getId());
            }
            scheduleDTO.setPetIds(petIds);
        }
        if (scheduleDTO.getEmployeeIds() == null) {
            List<Employee> employees = schedule.getEmployees();
            List<Long> employeeIds = new ArrayList<>();
            for (Employee e : employees) {
                employeeIds.add(e.getId());
            }
            scheduleDTO.setEmployeeIds(employeeIds);
        }

        return scheduleDTO;
    }
}
