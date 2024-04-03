package it.unimib.icasiduso.sportrack.model.exercise;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ExerciseCollection implements Parcelable {

    private final List<Exercise> exercises;

    public ExerciseCollection(){
        this.exercises = new ArrayList<>();
    }

    public ExerciseCollection(List<Exercise> exercises){
        this.exercises = exercises;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    protected ExerciseCollection(Parcel in, List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public final Creator<ExerciseCollection> CREATOR = new Creator<ExerciseCollection>() {
        @Override
        public ExerciseCollection createFromParcel(Parcel in) {
            return new ExerciseCollection(in, exercises);
        }

        @Override
        public ExerciseCollection[] newArray(int size) {
            return new ExerciseCollection[size];
        }
    };

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Exercises{\n");
        for (Exercise exercise : exercises) {
            stringBuilder.append("\t").append(exercise.toString()).append("\n");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    //TODO
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
}
