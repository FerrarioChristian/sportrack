package it.unimib.icasiduso.sportrack.model.schedule;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;


@Entity
public class Schedule{
    @PrimaryKey (autoGenerate = true)
    private long scheduleId;
    private String name;
    private String description;
    public Schedule(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}