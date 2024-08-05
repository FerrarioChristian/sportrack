package it.unimib.icasiduso.sportrack.data.source.workout_exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public interface WorkoutExerciseCallback {
    void onSuccessFromLocal(List<WorkoutExercise> workoutExercises);
}
