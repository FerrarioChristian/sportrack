package it.unimib.icasiduso.sportrack.model.exercise;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class ExerciseCompleted implements Parcelable {

    public static final Creator<ExerciseCompleted> CREATOR = new Creator<ExerciseCompleted>() {
        @Override
        public ExerciseCompleted createFromParcel(Parcel in) {
            return new ExerciseCompleted(in);
        }

        @Override
        public ExerciseCompleted[] newArray(int size) {
            return new ExerciseCompleted[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String userId;
    private long workoutExerciseId;
    private long exerciseId;
    private String date;

    protected ExerciseCompleted(Parcel in) {
        workoutExerciseId = in.readLong();
        userId = in.readString();
        exerciseId = in.readLong();
        date = in.readString();
    }

    @Ignore
    public ExerciseCompleted(String userId, long workoutExerciseId, long exerciseId,
                             String date) {
        this.workoutExerciseId = workoutExerciseId;
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.date = date;
    }

    @Ignore
    @JsonCreator
    public ExerciseCompleted(@JsonProperty("id") long id,
                             @JsonProperty("userId") String userId, @JsonProperty(
                                     "id") long workoutExerciseId, @JsonProperty(
                                             "exerciseId") long exerciseId,
                             @JsonProperty("date") String date) {
        this.id = id;
        this.userId = userId;
        this.workoutExerciseId = workoutExerciseId;
        this.exerciseId = exerciseId;
        this.date = date;
    }

    public ExerciseCompleted() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(workoutExerciseId);
        dest.writeString(userId);
        dest.writeLong(exerciseId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

