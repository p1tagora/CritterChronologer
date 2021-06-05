package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElse(null);
    }

    public List<Schedule> getAllSchedules() {
        return Lists.newArrayList(scheduleRepository.findAll());
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
}
