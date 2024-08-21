package it.unimib.icasiduso.sportrack.data.repository.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.source.exercise.IExerciseDataSource;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public class ExercisesRepository implements IExercisesRepository {

    private static final String TAG = ExercisesRepository.class.getSimpleName();

    private final IExerciseDataSource.Local exerciseLocalDataSource;
    private final IExerciseDataSource.Remote exerciseRemoteDataSource;

    public ExercisesRepository(
            IExerciseDataSource.Remote exerciseRemoteDataSource,
            IExerciseDataSource.Local exerciseLocalDataSource
    ) {
        this.exerciseRemoteDataSource = exerciseRemoteDataSource;
        this.exerciseLocalDataSource = exerciseLocalDataSource;
    }

    @Override
    public void getExercisesByMuscle(String muscle, GetExercisesCallback callback) {
        if (callback == null) return;
        //TODO: Implementare salvataggio e recupero online/offline
        exerciseRemoteDataSource.fetchExercisesByMuscle(muscle, new IExercisesRepository.GetExercisesCallback() {
            @Override
            public void onSuccess(List<Exercise> exercises) {
                saveExercises(exercises, callback);
                exerciseLocalDataSource.getExercises(muscle, callback);
            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }

    @Override
    public void getExerciseById(long id, GetExercisesCallback callback) {
        exerciseLocalDataSource.getExercise(id, callback);
    }

    @Override
    public void getExercisesByScheduleId(long scheduleId, GetExercisesCallback callback) {

    }

    @Override
    public void saveExercises(List<Exercise> exercises, GetExercisesCallback callback) {
        exerciseLocalDataSource.saveExercises(exercises, callback);
    }

}
