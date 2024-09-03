package it.unimib.icasiduso.sportrack.data.repository.schedule;


import java.util.List;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public interface IScheduleRepository {

    void getSchedules(String userId, ScheduleCallback callback);

    void newSchedule(Schedule schedule, ScheduleCallback callback);

    void deleteSchedule(Schedule schedule, ScheduleCallback callback);

    interface ScheduleCallback {
        void onSuccess();

        void onSuccess(List<Schedule> scheduleList);

        void onFailure(String errorMessage);
    }
}
