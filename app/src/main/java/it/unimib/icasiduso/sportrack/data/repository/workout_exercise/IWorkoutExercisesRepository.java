package it.unimib.icasiduso.sportrack.data.repository.workout_exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public interface IWorkoutExercisesRepository {

    void addWorkoutExercise(WorkoutExercise workoutExercise, SaveWorkoutExerciseCallback callback);

    void deleteWorkoutExercise(WorkoutExercise workoutExercise, SaveWorkoutExerciseCallback callback);

    void getWorkoutExercises(Long scheduleId, GetWorkoutExerciseCallback callback);

    interface SaveWorkoutExerciseCallback {
        void onSuccess();

        void onFailure(String errorMessage);
    }

    interface GetWorkoutExerciseCallback {
        void onWorkoutExercisesLoaded(List<WorkoutExercise> workoutExerciseList);

        void onDataNotAvailable();

        void onFailure(String errorMessage);
    }

}
