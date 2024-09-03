package it.unimib.icasiduso.sportrack.data.repository.schedule;

import it.unimib.icasiduso.sportrack.data.source.schedule.IScheduleDataSource;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public class ScheduleRepository implements IScheduleRepository {

    private static final String TAG = ScheduleRepository.class.getSimpleName();

    private final IScheduleDataSource.Local scheduleLocalDataSource;
    //TODO: Aggiungere remote
    // private final IScheduleDataSource.Remote scheduleRemoteDataSource;

    public ScheduleRepository(IScheduleDataSource.Local scheduleLocalDataSource/*, IScheduleDataSource.Remote scheduleRemoteDataSource */) {
        this.scheduleLocalDataSource = scheduleLocalDataSource;
        //this.scheduleRemoteDataSource = scheduleRemoteDataSource;
    }

    @Override
    public void getSchedules(String userId, IScheduleRepository.ScheduleCallback callback) {
        scheduleLocalDataSource.getSchedules(userId, callback);

    }

    @Override
    public void newSchedule(Schedule schedule, ScheduleCallback callback) {
        scheduleLocalDataSource.newSchedule(schedule, callback);
    }

    @Override
    public void deleteSchedule(Schedule schedule, ScheduleCallback callback) {
        scheduleLocalDataSource.deleteSchedule(schedule, callback);
    }

    @Override
    public void deleteUserSchedules(String userId, ScheduleCallback callback) {
        scheduleLocalDataSource.deleteUserSchedules(userId, callback);
    }


}