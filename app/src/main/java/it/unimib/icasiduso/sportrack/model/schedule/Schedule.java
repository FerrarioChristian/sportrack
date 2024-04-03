package it.unimib.icasiduso.sportrack.model.schedule;


import java.util.Date;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCollection;

public class Schedule {
    private String schedule_name;
    private String schedule_description;
    private Date schedule_date;
    private ExerciseCollection exerciseCollection;

    public Schedule() {
        exerciseCollection = new ExerciseCollection();
    }

    public void addExercise(Exercise exercise) {
        exerciseCollection.addExercise(exercise);
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public String getSchedule_description() {
        return schedule_description;
    }

    public void setSchedule_description(String schedule_description) {
        this.schedule_description = schedule_description;
    }

    public Date getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(Date schedule_date) {
        this.schedule_date = schedule_date;
    }

    public ExerciseCollection getExerciseCollection() {
        return exerciseCollection;
    }

    public void setExerciseCollection(ExerciseCollection exerciseCollection) {
        this.exerciseCollection = exerciseCollection;
    }
}
