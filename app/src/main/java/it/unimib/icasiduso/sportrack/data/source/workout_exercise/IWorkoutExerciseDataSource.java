package it.unimib.icasiduso.sportrack.data.source.workout_exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public interface IWorkoutExerciseDataSource {
    interface Remote {
        void addWorkoutExercise(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.SaveWorkoutExerciseCallback callback);

        void deleteWorkoutExercise(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.SaveWorkoutExerciseCallback callback);

        void getWorkoutExercises(long scheduleId, IWorkoutExercisesRepository.GetWorkoutExerciseCallback callback);
    }

    interface Local {
        void addWorkoutExercise(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.SaveWorkoutExerciseCallback callback);

        void deleteWorkoutExercise(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.SaveWorkoutExerciseCallback callback);

        void getWorkoutExercises(long scheduleId, IWorkoutExercisesRepository.GetWorkoutExerciseCallback callback);

        void updateWorkoutExercises(List<WorkoutExercise> workoutExerciseList);

        void deleteWorkoutExercises(long scheduleId);
    }
}
