package it.unimib.icasiduso.sportrack.controller;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;
import it.unimib.icasiduso.sportrack.model.exercise.ScheduledExercise;
import it.unimib.icasiduso.sportrack.repository.ScheduleRepository;

public class ScheduleController {
    private ScheduleRepository scheduleRepository;

    public ScheduleController(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> retrieveSchedules() {
        return this.scheduleRepository.getSchedules();
    }

    public void
}
