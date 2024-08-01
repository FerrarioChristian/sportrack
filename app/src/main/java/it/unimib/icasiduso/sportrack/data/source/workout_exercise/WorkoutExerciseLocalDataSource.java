package it.unimib.icasiduso.sportrack.data.source.workout_exercise;

import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.database.WorkoutExerciseDao;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public class WorkoutExerciseLocalDataSource extends BaseWorkoutExerciseLocalDataSource {
    protected WorkoutExerciseDao workoutExerciseDao;
    public WorkoutExerciseLocalDataSource(ExerciseRoomDatabase exerciseRoomDatabase) {
        this.workoutExerciseDao = exerciseRoomDatabase.workoutExerciseDao();
    }

    @Override
    public void addWorkoutExercise(WorkoutExercise workoutExercise) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.insertAll(workoutExercise);
            workoutExerciseRepositoryCallbackable.onSuccess();
        });
    }

    @Override
    public void deleteWorkoutExercise(WorkoutExercise workoutExercise) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.deleteWorkoutExercise(workoutExercise);
            workoutExerciseRepositoryCallbackable.onSuccess();
        });
    }

    @Override
    public void getWorkoutExercisesByScheduleId(long scheduleId) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseRepositoryCallbackable.onSuccess(workoutExerciseDao.getWorkoutExerciseByScheduleId(scheduleId));
        });
    }

    @Override
    public void deleteWorkoutExercisesByScheduleId(long scheduleId) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.deleteWorkoutExercisesByScheduleId(scheduleId);
            workoutExerciseRepositoryCallbackable.onSuccess();
        });
    }
}
