package it.unimib.icasiduso.sportrack.model.exercise;

import static java.lang.Integer.parseInt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WorkoutExercise{
    @PrimaryKey (autoGenerate = true)
    private long WorkoutExerciseId;
    private  int series;
    private int repetitions;
    private long externalExerciseId;
    private long externalScheduleId;

    public long getExternalScheduleId() {
        return externalScheduleId;
    }

    public void setExternalScheduleId(long scheduleId) {
        this.externalScheduleId = scheduleId;
    }

    public WorkoutExercise(String series, String reps, long externalExerciseId, long externalScheduleId) {
        this.series = parseInt(series);
        this.repetitions = parseInt(reps);
        this.externalExerciseId = externalExerciseId;
        this.externalScheduleId = externalScheduleId;
    }

    public long getWorkoutExerciseId() {
        return WorkoutExerciseId;
    }

    public void setWorkoutExerciseId(long scheduledExcerciseId) {
        this.WorkoutExerciseId = scheduledExcerciseId;
    }

    public long getExternalExerciseId() {
        return externalExerciseId;
    }

    public void setExternalExerciseId(long externalExerciseId) {
        this.externalExerciseId = externalExerciseId;
    }

    public WorkoutExercise() {}

    /*public Exercise getExercise(){
        return Exercise
    }*/

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
}