package it.unimib.icasiduso.sportrack.data.repository.workout_exercise;

import it.unimib.icasiduso.sportrack.data.repository.exercise.ExerciseRepository;
import it.unimib.icasiduso.sportrack.data.source.workout_exercise.IWorkoutExerciseDataSource;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public class WorkoutExercisesRepository implements IWorkoutExercisesRepository {

    private static final String TAG = ExerciseRepository.class.getSimpleName();

    private final IWorkoutExerciseDataSource.Local workoutExerciseLocalDataSource;
    //private final IWorkoutExerciseDataSource.Remote workoutExerciseRemoteDataSource;

    public WorkoutExercisesRepository(
            // IWorkoutExerciseDataSource.Remote workoutExerciseRemoteDataSource,
            IWorkoutExerciseDataSource.Local workoutExerciseLocalDataSource
    ) {
        //this.workoutExerciseRemoteDataSource = workoutExerciseRemoteDataSource;
        this.workoutExerciseLocalDataSource = workoutExerciseLocalDataSource;
    }


    //TODO Verificare se serve una callback (e implementare db remoto)
    @Override
    public void addWorkoutExerciseToSchedule(WorkoutExercise workoutExercise, WorkoutExerciseCallback callback) {
        workoutExerciseLocalDataSource.addWorkoutExerciseToSchedule(workoutExercise);
    }

    @Override
    public void deleteWorkoutExerciseFromSchedule(WorkoutExercise workoutExercise, WorkoutExerciseCallback callback) {
        workoutExerciseLocalDataSource.deleteWorkoutExerciseFromSchedule(workoutExercise, callback);
    }

    @Override
    public void getWorkoutExercisesByScheduleId(Long scheduleId, WorkoutExerciseCallback callback) {
        workoutExerciseLocalDataSource.getWorkoutExercisesByScheduleId(scheduleId, callback);
    }
}

