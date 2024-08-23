package it.unimib.icasiduso.sportrack.data.repository.workout_exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public interface IWorkoutExercisesRepository {
    void addWorkoutExerciseToSchedule(WorkoutExercise workoutExercise);

    void deleteWorkoutExerciseFromSchedule(WorkoutExercise workoutExercise);

    void getWorkoutExerciseByScheduleId(Long scheduleId);

    interface WorkoutExerciseCallback {
        void onSuccess(List<WorkoutExercise> workoutExercises);

        void onSuccess();

        void onFailure(Exception exception);
    }
}
