package it.unimib.icasiduso.sportrack.data.source.schedule;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public interface ScheduleCallback {
    void onSuccesFromLocal(List<Schedule> schedules);
    void onFailureFromLocal();
}
