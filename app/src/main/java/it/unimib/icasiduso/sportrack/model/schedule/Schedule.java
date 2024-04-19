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
    private Date date;
    public Schedule() {}

}