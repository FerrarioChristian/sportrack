package it.unimib.icasiduso.sportrack.data.repository.exercise;


import java.util.List;

import it.unimib.icasiduso.sportrack.data.source.exercise.IExerciseDataSource;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public class ExerciseRepository implements IExerciseRepository {

    private static final String TAG = ExerciseRepository.class.getSimpleName();

    private final IExerciseDataSource.Local exerciseLocalDataSource;
    private final IExerciseDataSource.Remote exerciseRemoteDataSource;

    public ExerciseRepository(
            IExerciseDataSource.Remote exerciseRemoteDataSource,
            IExerciseDataSource.Local exerciseLocalDataSource
    ) {
        this.exerciseRemoteDataSource = exerciseRemoteDataSource;
        this.exerciseLocalDataSource = exerciseLocalDataSource;
    }

    @Override
    public void getExercisesByMuscle(String muscle, final GetExercisesCallback callback) {
        if (callback == null) return;

        exerciseLocalDataSource.getExercises(muscle, new GetExercisesCallback() {
            @Override
            public void onSuccess(List<Exercise> exercises) {
               callback.onSuccess(exercises);
            }

            @Override
            public void onDataNotAvailable() {
               fetchExercisesByMuscle(muscle, callback);
            }

            @Override
            public void onFailure(Exception exception) {
                callback.onFailure(exception);
            }
        });

    }

    private void fetchExercisesByMuscle(String muscle, final GetExercisesCallback callback) {
        exerciseRemoteDataSource.fetchExercisesByMuscle(muscle, new GetExercisesCallback() {

            @Override
            public void onSuccess(List<Exercise> exercises) {
                saveExercises(exercises, callback);
                exerciseLocalDataSource.getExercises(muscle, callback);
            }

            @Override
            public void onDataNotAvailable() {}

            @Override
            public void onFailure(Exception exception) {
                callback.onFailure(exception);
            }
        });
    }

    @Override
    public void getExerciseById(long id, final ExercisesCallback callback) {
        if (callback == null) return;
        exerciseLocalDataSource.getExercise(id, callback);
    }

    @Override
    public void saveExercises(List<Exercise> exercises, final GetExercisesCallback callback) {
        exerciseLocalDataSource.saveExercises(exercises, callback);
    }

}
