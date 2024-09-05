package it.unimib.icasiduso.sportrack.data.repository.schedule;


import java.util.List;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public interface IScheduleRepository {

    void getSchedules(String userId, GetScheduleCallback callback);

    void newSchedule(Schedule schedule, SaveScheduleCallback callback);

    void deleteSchedule(Schedule schedule, SaveScheduleCallback callback);

    void deleteUserSchedules(String userId, SaveScheduleCallback callback);

    interface SaveScheduleCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    interface GetScheduleCallback {
        void onSchedulesLoaded(List<Schedule> scheduleList);
        void onDataNotAvailable();
        void onFailure(String errorMessage);
    }
}
