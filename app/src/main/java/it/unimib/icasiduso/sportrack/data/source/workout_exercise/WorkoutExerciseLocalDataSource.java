package it.unimib.icasiduso.sportrack.data.source.workout_exercise;

import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.database.WorkoutExerciseDao;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public class WorkoutExerciseLocalDataSource implements IWorkoutExerciseDataSource.Local {
    protected WorkoutExerciseDao workoutExerciseDao;

    public WorkoutExerciseLocalDataSource(ExerciseRoomDatabase exerciseRoomDatabase) {
        this.workoutExerciseDao = exerciseRoomDatabase.workoutExerciseDao();
    }

    @Override
    public void addWorkoutExerciseToSchedule(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.WorkoutExerciseCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.insertAll(workoutExercise);
            callback.onSuccess();
        });
    }

    @Override
    public void deleteWorkoutExerciseFromSchedule(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.WorkoutExerciseCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.deleteWorkoutExercise(workoutExercise);
            callback.onSuccess();
        });
    }

    @Override
    public void getWorkoutExercisesByScheduleId(long scheduleId, IWorkoutExercisesRepository.WorkoutExerciseCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            callback.onSuccess(workoutExerciseDao.getWorkoutExerciseByScheduleId(scheduleId));
        });
    }

    @Override
    public void deleteWorkoutExercisesByScheduleId(long scheduleId, IWorkoutExercisesRepository.WorkoutExerciseCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.deleteWorkoutExercisesByScheduleId(scheduleId);
            callback.onSuccess();
        });
    }
}
