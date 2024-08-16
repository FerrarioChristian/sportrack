package it.unimib.icasiduso.sportrack.data.source.workout_exercise;

import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public interface IWorkoutExerciseDataSource {
    interface Remote {
        void addWorkoutExerciseToSchedule(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.WorkoutExerciseCallback callback);
        void deleteWorkoutExerciseFromSchedule(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.WorkoutExerciseCallback callback);
        void getWorkoutExercisesByScheduleId(long scheduleId, IWorkoutExercisesRepository.WorkoutExerciseCallback callback);
        void deleteWorkoutExercisesByScheduleId(long scheduleId, IWorkoutExercisesRepository.WorkoutExerciseCallback callback);

    }

    interface Local {
        void addWorkoutExerciseToSchedule(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.WorkoutExerciseCallback callback);
        void deleteWorkoutExerciseFromSchedule(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.WorkoutExerciseCallback callback);
        void getWorkoutExercisesByScheduleId(long scheduleId, IWorkoutExercisesRepository.WorkoutExerciseCallback callback);
        void deleteWorkoutExercisesByScheduleId(long scheduleId, IWorkoutExercisesRepository.WorkoutExerciseCallback callback);
    }
}
