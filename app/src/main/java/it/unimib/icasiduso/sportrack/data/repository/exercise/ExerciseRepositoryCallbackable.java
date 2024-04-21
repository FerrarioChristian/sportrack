package it.unimib.icasiduso.sportrack.data.repository.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface ExerciseRepositoryCallbackable {
    void onSuccess(List<Exercise> exercises);
    void onFailure(String errorMessage);

}
