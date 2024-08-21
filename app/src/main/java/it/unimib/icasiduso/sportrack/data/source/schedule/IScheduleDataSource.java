package it.unimib.icasiduso.sportrack.data.source.schedule;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public interface IScheduleDataSource {
    interface Remote {
        void newSchedule(Schedule schedule, ScheduleCallback callback);
        void deleteSchedule(Schedule schedule, ScheduleCallback callback);
        void getUserSchedules(String userId, ScheduleCallback callback);
    }

    interface Local {
        void newSchedule(Schedule schedule, ScheduleCallback callback);
        void deleteSchedule(Schedule schedule, ScheduleCallback callback);
        void getUserSchedules(String userId, ScheduleCallback callback);
    }
}
