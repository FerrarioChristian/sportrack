package it.unimib.icasiduso.sportrack.model.exercise;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import java.util.Date;

@Entity(tableName = "exercise_completed")
public class ExerciseCompleted {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "workout_exercise_id")
    private long workoutExerciseId;

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo(name = "external_exercise_id")
    private long externalExerciseId;

    @ColumnInfo(name = "date")
    private Date date;

    // Getters and setters
    public long getWorkoutExerciseId() {
        return workoutExerciseId;
    }

    public void setWorkoutExerciseId(long workoutExerciseId) {
        this.workoutExerciseId = workoutExerciseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getExternalExerciseId() {
        return externalExerciseId;
    }

    public void setExternalExerciseId(long externalExerciseId) {
        this.externalExerciseId = externalExerciseId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

