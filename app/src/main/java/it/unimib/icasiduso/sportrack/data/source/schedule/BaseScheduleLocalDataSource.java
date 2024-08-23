package it.unimib.icasiduso.sportrack.data.source.schedule;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public abstract class BaseScheduleLocalDataSource {
    protected ScheduleCallback scheduleCallback;

    public void setScheduleCallback(ScheduleCallback scheduleCallback) {
        this.scheduleCallback = scheduleCallback;
    }

    public abstract void getSchedules();

    public abstract void addSchedule(Schedule schedule);

    public abstract void deleteSchedule(Schedule schedule);


}
