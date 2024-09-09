package it.unimib.icasiduso.sportrack.model.exercise;

import static java.lang.Integer.parseInt;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

@Entity(foreignKeys = @ForeignKey(entity = Schedule.class, parentColumns = {"userId", "id"},
        childColumns = {"userId", "scheduleId"}, onDelete = ForeignKey.CASCADE), indices =
        {@Index({"userId", "scheduleId"})})
public class WorkoutExercise {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private int series;
    private int repetitions;
    private long exerciseId;
    private String userId;
    private long scheduleId;

    @Ignore
    public WorkoutExercise(String series, String reps, long externalExerciseId,
                           long externalScheduleId, String userId) {
        this.series = parseInt(series);
        this.repetitions = parseInt(reps);
        this.exerciseId = externalExerciseId;
        this.scheduleId = externalScheduleId;
        this.userId = userId;
    }

    public WorkoutExercise() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
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