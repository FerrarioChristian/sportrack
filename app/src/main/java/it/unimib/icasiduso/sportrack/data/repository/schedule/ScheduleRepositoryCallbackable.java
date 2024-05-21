package it.unimib.icasiduso.sportrack.data.repository.schedule;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public interface ScheduleRepositoryCallbackable {
    void onSuccess(List<Schedule> scheduleList);

    void onFailure(String errorMessage);
}
