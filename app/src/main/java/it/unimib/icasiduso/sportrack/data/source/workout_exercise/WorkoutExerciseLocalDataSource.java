package it.unimib.icasiduso.sportrack.data.source.workout_exercise;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.database.WorkoutExerciseDao;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public class WorkoutExerciseLocalDataSource implements IWorkoutExerciseDataSource.Local {

    private final WorkoutExerciseDao workoutExerciseDao;

    public WorkoutExerciseLocalDataSource(ExerciseRoomDatabase exerciseRoomDatabase) {
        this.workoutExerciseDao = exerciseRoomDatabase.workoutExerciseDao();
    }

    @Override
    public void addWorkoutExercise(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.SaveWorkoutExerciseCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.insertAll(workoutExercise);
            callback.onSuccess();
        });
    }

    @Override
    public void deleteWorkoutExercise(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.SaveWorkoutExerciseCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.deleteWorkoutExercise(workoutExercise);
            callback.onSuccess();
        });
    }

    @Override
    public void getWorkoutExercises(long scheduleId, IWorkoutExercisesRepository.GetWorkoutExerciseCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<WorkoutExercise> workoutExerciseList = workoutExerciseDao.getWorkoutExercisesByScheduleId(
                    scheduleId);
            if (workoutExerciseList == null || workoutExerciseList.isEmpty()) {
                callback.onDataNotAvailable();
            } else {
                callback.onWorkoutExercisesLoaded(workoutExerciseList);
            }
        });
    }

    @Override
    public void updateWorkoutExercises(List<WorkoutExercise> workoutExerciseList) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<WorkoutExercise> oldWorkoutExerciseList = workoutExerciseDao.getWorkoutExercisesByScheduleId(
                    workoutExerciseList.get(0).getScheduleId());

            List<WorkoutExercise> exercisesToDelete = new ArrayList<>(oldWorkoutExerciseList);
            exercisesToDelete.removeAll(workoutExerciseList);

            for (WorkoutExercise workoutExercise : exercisesToDelete) {
                workoutExerciseDao.deleteWorkoutExercise(workoutExercise);
            }

            workoutExerciseDao.insertWorkoutExercises(workoutExerciseList);
        });
    }

    @Override
    public void deleteWorkoutExercises(long scheduleId) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.deleteWorkoutExercisesByScheduleId(scheduleId);
        });
    }

}
