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
    public void newSchedule(Schedule schedule, IScheduleRepository.ScheduleCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Schedule> scheduleList = new ArrayList<>();
            scheduleList.add(schedule);
            scheduleDao.insertScheduleList(scheduleList);
            callback.onSuccess(scheduleDao.getSchedulesByUserId(schedule.getUserId()));
        });
    }

    @Override
    public void deleteSchedule(Schedule schedule, IScheduleRepository.ScheduleCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            scheduleDao.deleteSchedule(schedule);
            callback.onSuccess();
        });
    }

    @Override
    public void getSchedules(String userId, IScheduleRepository.ScheduleCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> callback.onSuccess(scheduleDao.getSchedulesByUserId(userId)));
    }

    @Override
    public void deleteUserSchedules(String userId, IScheduleRepository.ScheduleCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> scheduleDao.deleteUserSchedules(userId));
    }


}
