package it.unimib.icasiduso.sportrack.data.repository.schedule;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.source.schedule.IScheduleDataSource;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public class ScheduleRepository implements IScheduleRepository {

    private static final String TAG = ScheduleRepository.class.getSimpleName();

    private final IScheduleDataSource.Local scheduleLocalDataSource;
    private final IScheduleDataSource.Remote scheduleRemoteDataSource;

    public ScheduleRepository(IScheduleDataSource.Local scheduleLocalDataSource,
                              IScheduleDataSource.Remote scheduleRemoteDataSource) {
        this.scheduleLocalDataSource = scheduleLocalDataSource;
        this.scheduleRemoteDataSource = scheduleRemoteDataSource;
    }

    @Override
    public void getSchedules(String userId, IScheduleRepository.GetScheduleCallback callback) {
        scheduleRemoteDataSource.getSchedules(userId, new GetScheduleCallback() {

            @Override
            public void onSchedulesLoaded(List<Schedule> scheduleList) {
                callback.onSchedulesLoaded(scheduleList);
                if (scheduleList.isEmpty()) {
                    scheduleLocalDataSource.deleteUserSchedules(userId);
                } else {
                    scheduleLocalDataSource.updateSchedules(scheduleList);
                }
            }

            @Override
            public void onDataNotAvailable() {
                scheduleLocalDataSource.getSchedules(userId, callback);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

    @Override
    public void newSchedule(Schedule schedule, SaveScheduleCallback callback) {
        long scheduleId = System.currentTimeMillis();
        schedule.setScheduleId(scheduleId);

        scheduleLocalDataSource.newSchedule(schedule, callback);
        scheduleRemoteDataSource.newSchedule(schedule, new SaveScheduleCallback() {

            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

    @Override
    public void deleteSchedule(Schedule schedule, SaveScheduleCallback callback) {
        scheduleLocalDataSource.deleteSchedule(schedule, callback);
        scheduleRemoteDataSource.deleteSchedule(schedule, new SaveScheduleCallback() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

    @Override
    public void deleteUserSchedules(String userId, SaveScheduleCallback callback) {

        scheduleRemoteDataSource.deleteUserSchedules(userId, new SaveScheduleCallback() {

            @Override
            public void onSuccess() {
                scheduleLocalDataSource.deleteUserSchedules(userId, callback);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

    }


}