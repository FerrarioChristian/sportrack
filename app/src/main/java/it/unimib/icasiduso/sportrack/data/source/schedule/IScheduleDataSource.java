package it.unimib.icasiduso.sportrack.data.source.schedule;

import it.unimib.icasiduso.sportrack.data.repository.schedule.IScheduleRepository;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public interface IScheduleDataSource {
    interface Remote {
        void newSchedule(Schedule schedule, IScheduleRepository.ScheduleCallback callback);

        void deleteSchedule(Schedule schedule, IScheduleRepository.ScheduleCallback callback);

        void getSchedules(String userId, IScheduleRepository.ScheduleCallback callback);
    }

    interface Local {
        void newSchedule(Schedule schedule, IScheduleRepository.ScheduleCallback callback);

        void deleteSchedule(Schedule schedule, IScheduleRepository.ScheduleCallback callback);

        void getSchedules(String userId, IScheduleRepository.ScheduleCallback callback);
    }
}
