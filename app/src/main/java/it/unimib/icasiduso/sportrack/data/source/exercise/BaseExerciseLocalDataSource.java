package it.unimib.icasiduso.sportrack.data.source.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public abstract class BaseExerciseLocalDataSource {
    protected ExerciseCallback exerciseCallback;

    public void setExerciseCallback(ExerciseCallback exerciseCallback){
        this.exerciseCallback = exerciseCallback;
    }

    public abstract void getExercises(String muscle);

    public abstract void getExercise(long id);

    public abstract void saveExercises(List<Exercise> exercises);


}
