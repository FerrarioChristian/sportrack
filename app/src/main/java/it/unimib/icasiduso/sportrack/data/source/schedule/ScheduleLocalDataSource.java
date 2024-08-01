package it.unimib.icasiduso.sportrack.data.source.schedule;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.database.ScheduleDao;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public class ScheduleLocalDataSource extends BaseScheduleLocalDataSource {

    private ScheduleDao scheduleDao;

    public ScheduleLocalDataSource(ExerciseRoomDatabase exerciseRoomDatabase) {
        this.scheduleDao = exerciseRoomDatabase.scheduleDao();
    }


    @Override
    public void getSchedules() {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            scheduleCallback.onSuccesFromLocal(scheduleDao.getAll());
        });
    }

    @Override
    public void addSchedule(Schedule schedule) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Schedule> scheduleList = new ArrayList<>();
            scheduleList.add(schedule);
            scheduleDao.insertScheduleList(scheduleList);
            scheduleCallback.onSuccesFromLocal(scheduleDao.getAll());
        });
    }

    @Override
    public void deleteSchedule(Schedule schedule) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            scheduleDao.deleteSchedule(schedule);
            scheduleCallback.onSuccesFromLocal(scheduleDao.getAll());
        });
    }
}
