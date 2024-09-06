package it.unimib.icasiduso.sportrack.data.source.schedule;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.schedule.IScheduleRepository;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public interface IScheduleDataSource {
    interface Remote {
        void newSchedule(Schedule schedule, IScheduleRepository.SaveScheduleCallback callback);

        void deleteSchedule(Schedule schedule, IScheduleRepository.SaveScheduleCallback callback);

        void getSchedules(String userId, IScheduleRepository.GetScheduleCallback callback);

        void deleteUserSchedules(String userId, IScheduleRepository.SaveScheduleCallback callback);
    }

    interface Local {
        void newSchedule(Schedule schedule, IScheduleRepository.SaveScheduleCallback callback);

        void deleteSchedule(Schedule schedule, IScheduleRepository.SaveScheduleCallback callback);

        void getSchedules(String userId, IScheduleRepository.GetScheduleCallback callback);

        void deleteUserSchedules(String userId, IScheduleRepository.SaveScheduleCallback callback);

        void updateSchedules(List<Schedule> schedules);
    }
}
