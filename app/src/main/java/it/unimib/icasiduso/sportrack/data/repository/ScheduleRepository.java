package it.unimib.icasiduso.sportrack.data.repository;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public class ScheduleRepository {
    private List<Schedule> schedules;

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}