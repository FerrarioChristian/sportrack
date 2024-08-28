package it.unimib.icasiduso.sportrack.data.repository.exercise;

import android.widget.Toast;

import java.util.List;

import it.unimib.icasiduso.sportrack.App;
import it.unimib.icasiduso.sportrack.R;
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
    public void getExercisesByMuscle(String muscle, ExercisesCallback callback) {
        if (callback == null) return;
        //TODO: Implementare salvataggio e recupero online/offline
        exerciseRemoteDataSource.fetchExercisesByMuscle(muscle, new IExercisesRepository.ExercisesCallback() {
            @Override
            public void onSuccess(Exercise exercise) {}

            @Override
            public void onSuccess(List<Exercise> exercises) {
                saveExercises(exercises, callback);
                exerciseLocalDataSource.getExercises(muscle, callback);
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(App.getInstance(), App.getRes().getString(R.string.unexpected_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getExerciseById(long id, ExercisesCallback callback) {
        if (callback == null) return;
        exerciseLocalDataSource.getExercise(id, callback);
    }

    @Override
    public void getExercisesByScheduleId(long scheduleId, ExercisesCallback callback) {

    }

    @Override
    public void saveExercises(List<Exercise> exercises, ExercisesCallback callback) {
        //TODO gestire mancanza di connessione
        exerciseLocalDataSource.saveExercises(exercises, callback);
        exerciseRemoteDataSource.saveExercises(exercises, callback);
    }

}
