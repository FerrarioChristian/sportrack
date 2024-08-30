package it.unimib.icasiduso.sportrack.model.exercise;

import static java.lang.Integer.parseInt;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

@Entity(foreignKeys = @ForeignKey(entity = Schedule.class, parentColumns = "scheduleId", childColumns = "externalScheduleId", onDelete = ForeignKey.CASCADE), indices = {@Index("externalScheduleId")})
public class WorkoutExercise {
    @PrimaryKey(autoGenerate = true)
    private long WorkoutExerciseId;
    private int series;
    private int repetitions;
    private long externalExerciseId;
    private long externalScheduleId;

    public WorkoutExercise(String series, String reps, long externalExerciseId, long externalScheduleId) {
        this.series = parseInt(series);
        this.repetitions = parseInt(reps);
        this.externalExerciseId = externalExerciseId;
        this.externalScheduleId = externalScheduleId;
    }

    public WorkoutExercise() {
    }

    public long getExternalScheduleId() {
        return externalScheduleId;
    }

    public void setExternalScheduleId(long scheduleId) {
        this.externalScheduleId = scheduleId;
    }

    public long getWorkoutExerciseId() {
        return WorkoutExerciseId;
    }

    public void setWorkoutExerciseId(long scheduledExerciseId) {
        this.WorkoutExerciseId = scheduledExerciseId;
    }

    public long getExternalExerciseId() {
        return externalExerciseId;
    }

    public void setExternalExerciseId(long externalExerciseId) {
        this.externalExerciseId = externalExerciseId;
    }

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