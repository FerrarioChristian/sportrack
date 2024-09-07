package it.unimib.icasiduso.sportrack.data.repository.workout_exercise;

import android.util.Log;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.exercise.ExerciseRepository;
import it.unimib.icasiduso.sportrack.data.source.workout_exercise.IWorkoutExerciseDataSource;
import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCompleted;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public class WorkoutExercisesRepository implements IWorkoutExercisesRepository {

    private static final String TAG = ExerciseRepository.class.getSimpleName();

    private final IWorkoutExerciseDataSource.Local workoutExerciseLocalDataSource;
    private final IWorkoutExerciseDataSource.Remote workoutExerciseRemoteDataSource;

    public WorkoutExercisesRepository(IWorkoutExerciseDataSource.Local workoutExerciseLocalDataSource, IWorkoutExerciseDataSource.Remote workoutExerciseRemoteDataSource) {
        this.workoutExerciseLocalDataSource = workoutExerciseLocalDataSource;
        this.workoutExerciseRemoteDataSource = workoutExerciseRemoteDataSource;
    }


    @Override
    public void addWorkoutExercise(WorkoutExercise workoutExercise,
                                   SaveWorkoutExerciseCallback callback) {
        long workoutExerciseId = System.currentTimeMillis();
        workoutExercise.setWorkoutExerciseId(workoutExerciseId);

        workoutExerciseLocalDataSource.addWorkoutExercise(workoutExercise, callback);
        workoutExerciseRemoteDataSource.addWorkoutExercise(workoutExercise,
                new SaveWorkoutExerciseCallback() {

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });
    }

    @Override
    public void deleteWorkoutExercise(WorkoutExercise workoutExercise,
                                      SaveWorkoutExerciseCallback callback) {
        workoutExerciseLocalDataSource.deleteWorkoutExercise(workoutExercise, callback);
        workoutExerciseRemoteDataSource.deleteWorkoutExercise(workoutExercise,
                new SaveWorkoutExerciseCallback() {

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });

    }

    @Override
    public void getWorkoutExercises(Long scheduleId, GetWorkoutExerciseCallback callback) {
        workoutExerciseRemoteDataSource.getWorkoutExercises(scheduleId,
                new GetWorkoutExerciseCallback() {

                    @Override
                    public void onWorkoutExercisesLoaded(List<WorkoutExercise> workoutExerciseList) {
                        callback.onWorkoutExercisesLoaded(workoutExerciseList);
                        if (workoutExerciseList.isEmpty()) {
                            workoutExerciseLocalDataSource.deleteWorkoutExercises(scheduleId);
                        } else {
                            workoutExerciseLocalDataSource.updateWorkoutExercises(
                                    workoutExerciseList);
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        workoutExerciseLocalDataSource.getWorkoutExercises(scheduleId, callback);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e(TAG, errorMessage);

                    }
                });
    }

    @Override
    public void saveExerciseCompleted(ExerciseCompleted exerciseCompleted) {
        workoutExerciseLocalDataSource.saveExerciseCompleted(exerciseCompleted);
    }

    @Override
    public void getExercisesCompleted(String userId, GetExercisesCompletedCallback callback) {
        workoutExerciseLocalDataSource.getExercisesCompleted(userId, callback);

    }


}

