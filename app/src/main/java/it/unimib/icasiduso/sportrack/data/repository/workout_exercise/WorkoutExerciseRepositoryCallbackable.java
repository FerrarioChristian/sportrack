package it.unimib.icasiduso.sportrack.data.repository.workout_exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.exercise.ExerciseRepositoryCallbackable;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public interface WorkoutExerciseRepositoryCallbackable {
    void onSuccess();
    void onSuccess(List<WorkoutExercise> exercises);
    void onFailure(String errorMessage);

}
