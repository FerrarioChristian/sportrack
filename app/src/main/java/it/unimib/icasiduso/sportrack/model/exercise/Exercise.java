package it.unimib.icasiduso.sportrack.model.exercise;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Entity
public class Exercise implements Parcelable {
    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
    @PrimaryKey
    private long exerciseId;
    private String name;
    private String type;
    private String muscle;
    private String equipment;
    private String difficulty;
    private String instructions;

    @Ignore
    @JsonCreator
    public Exercise(
            @JsonProperty("name") String name,
            @JsonProperty("type") String type,
            @JsonProperty("muscle") String muscle,
            @JsonProperty("equipment") String equipment,
            @JsonProperty("difficulty") String difficulty,
            @JsonProperty("instructions") String instructions) {
        this.name = name;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.exerciseId = generateExerciseId(name);
    }

    private long generateExerciseId(String name) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(name.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            return Math.abs(no.longValue());
        } catch (NoSuchAlgorithmException e) {
            return 0;
        }
    }

    public Exercise() {
    }

    protected Exercise(Parcel in) {
        name = in.readString();
        type = in.readString();
        muscle = in.readString();
        equipment = in.readString();
        difficulty = in.readString();
        instructions = in.readString();
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(name, exercise.name) && Objects.equals(type, exercise.type) && Objects.equals(muscle, exercise.muscle) && Objects.equals(equipment, exercise.equipment) && Objects.equals(difficulty, exercise.difficulty) && Objects.equals(instructions, exercise.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, muscle, equipment, difficulty, instructions);
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", muscle='" + muscle + '\'' +
                ", equipment='" + equipment + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(muscle);
        dest.writeString(equipment);
        dest.writeString(difficulty);
        dest.writeString(instructions);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
