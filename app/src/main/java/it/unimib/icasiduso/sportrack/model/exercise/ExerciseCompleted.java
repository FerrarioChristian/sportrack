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
    private long workoutExerciseId;
    private String userId;
    private long externalExerciseId;
    private String date;

    protected ExerciseCompleted(Parcel in) {
        workoutExerciseId = in.readLong();
        userId = in.readString();
        externalExerciseId = in.readLong();
        date = in.readString();
    }

    @Ignore
    @JsonCreator
    public ExerciseCompleted(@JsonProperty("userId") String userId, @JsonProperty("workoutExerciseId") long workoutExerciseId, @JsonProperty("exerciseId") long externalExerciseId, @JsonProperty("date") String date) {
        this.userId = userId;
        this.workoutExerciseId = workoutExerciseId;
        this.externalExerciseId = externalExerciseId;
        this.date = date;
    }

    public ExerciseCompleted() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(workoutExerciseId);
        dest.writeString(userId);
        dest.writeLong(externalExerciseId);
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

    public long getExternalExerciseId() {
        return externalExerciseId;
    }

    public void setExternalExerciseId(long externalExerciseId) {
        this.externalExerciseId = externalExerciseId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

