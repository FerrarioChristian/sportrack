package it.unimib.icasiduso.sportrack.data.source.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;


public interface ExerciseCallback {
    void onSuccessFromRemote(List<Exercise> exercises);
    void onFailureFromRemote(Exception exception);

    void onSuccessFromLocal(List<Exercise> exercises);
    void onFailureFromLocal(Exception exception);

}
