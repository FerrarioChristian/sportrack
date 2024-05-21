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

    private List<Schedule> schedules;
    private final Application application;
    private final ScheduleDao scheduleDao;

    private final ScheduleRepositoryCallbackable scheduleRepositoryCallbackable;

    public ScheduleRepository(Application application, ScheduleRepositoryCallbackable scheduleRepositoryCallbackable){
        this.application = application;
        ExerciseRoomDatabase exerciseRoomDatabase = ServiceLocator.getInstance().getExerciseDatabase(application);
        this.scheduleDao = exerciseRoomDatabase.scheduleDao();
        this.scheduleRepositoryCallbackable = scheduleRepositoryCallbackable;
    }

    public void fetchSchedules(){
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule("nome1", "desc1"));
        scheduleList.add(new Schedule("nome2", "desc2"));
        scheduleList.add(new Schedule("nome3", "desc3"));
        scheduleList.add(new Schedule("nome4", "desc4"));
        scheduleList.add(new Schedule("nome5", "desc5"));

        scheduleRepositoryCallbackable.onSuccess(scheduleList);
    }


    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}