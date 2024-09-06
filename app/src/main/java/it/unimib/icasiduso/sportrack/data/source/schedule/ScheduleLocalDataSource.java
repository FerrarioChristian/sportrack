package it.unimib.icasiduso.sportrack.data.source.schedule;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.database.ScheduleDao;
import it.unimib.icasiduso.sportrack.data.repository.schedule.IScheduleRepository;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public class ScheduleLocalDataSource implements IScheduleDataSource.Local {

    private final ScheduleDao scheduleDao;

    public ScheduleLocalDataSource(ExerciseRoomDatabase exerciseRoomDatabase) {
        this.scheduleDao = exerciseRoomDatabase.scheduleDao();
    }


    @Override
    public void newSchedule(Schedule schedule, IScheduleRepository.SaveScheduleCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Schedule> scheduleList = new ArrayList<>();
            scheduleList.add(schedule);
            scheduleDao.insertScheduleList(scheduleList);
            callback.onSuccess();
        });
    }

    @Override
    public void deleteSchedule(Schedule schedule, IScheduleRepository.SaveScheduleCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            scheduleDao.deleteSchedule(schedule);
            callback.onSuccess();
        });
    }

    @Override
    public void getSchedules(String userId, IScheduleRepository.GetScheduleCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Schedule> scheduleList = scheduleDao.getSchedulesByUserId(userId);
            if (scheduleList == null || scheduleList.isEmpty()) {
                callback.onDataNotAvailable();
            } else {
                callback.onSchedulesLoaded(scheduleList);
            }

        });
    }

    @Override
    public void deleteUserSchedules(String userId, IScheduleRepository.SaveScheduleCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            scheduleDao.deleteUserSchedules(userId);
            callback.onSuccess();
        });
    }

    @Override
    public void updateSchedules(List<Schedule> schedules) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            scheduleDao.insertScheduleList(schedules);
        });
    }


}
