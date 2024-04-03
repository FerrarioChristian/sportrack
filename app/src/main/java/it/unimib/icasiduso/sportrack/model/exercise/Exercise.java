package it.unimib.icasiduso.sportrack.model.exercise;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

@SuppressLint("ParcelCreator")
public class Exercise implements Parcelable {
    protected String name;
    protected String type;

    public Exercise(){}
    public Exercise(String name, String type, String muscle, String equipment, String difficulty, String instructions) {
        this.name = name;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
    }

    protected String muscle;
    protected String equipment;
    protected String difficulty;
    protected String instructions;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMuscle() {
        return muscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getInstructions() {
        return instructions;
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
    public int describeContents() {
        return 0;
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
}
