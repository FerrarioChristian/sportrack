package it.unimib.icasiduso.sportrack.data.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public class ScheduleWithWorkoutExercise {
    @Embedded
    public Schedule schedule;
    @Relation(parentColumn = "id", entityColumn = "scheduleId")
    public List<Schedule> schedules;
}
