package it.unimib.icasiduso.sportrack.data.repository.schedule;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.database.ScheduleDao;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;

public class ScheduleRepository {
    private static final String TAG = ScheduleRepository.class.getSimpleName();

    private final Application application;
    private final ScheduleDao scheduleDao;
    private final ScheduleRepositoryCallbackable scheduleRepositoryCallbackable;

    public ScheduleRepository(Application application, ScheduleRepositoryCallbackable scheduleRepositoryCallbackable) {
        this.application = application;
        ExerciseRoomDatabase exerciseRoomDatabase = ServiceLocator.getInstance().getExerciseDatabase(application);
        this.scheduleDao = exerciseRoomDatabase.scheduleDao();
        this.scheduleRepositoryCallbackable = scheduleRepositoryCallbackable;
    }
  
    public void fetchSchedules() {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            scheduleRepositoryCallbackable.onSuccess(scheduleDao.getAll());
        });
    }

    public void insertSchedule(Schedule schedule) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Schedule> scheduleList = new ArrayList<>();
            scheduleList.add(schedule);
            scheduleDao.insertScheduleList(scheduleList);
            scheduleRepositoryCallbackable.onSuccess(scheduleDao.getAll());
        });
    }
    public void deleteSchedule(Schedule schedule) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            scheduleDao.deleteSchedule(schedule);
            scheduleRepositoryCallbackable.onSuccess(scheduleDao.getAll());
        });
    }
}