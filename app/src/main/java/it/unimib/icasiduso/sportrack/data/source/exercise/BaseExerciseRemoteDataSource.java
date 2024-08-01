package it.unimib.icasiduso.sportrack.data.source.exercise;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public abstract class BaseExerciseRemoteDataSource {

    protected ExerciseCallback exerciseCallback;

    public void setExerciseCallback(ExerciseCallback exerciseCallback) {
        this.exerciseCallback = exerciseCallback;
    }

    public abstract void fetchExercisesByMuscle(String muscle);

}
