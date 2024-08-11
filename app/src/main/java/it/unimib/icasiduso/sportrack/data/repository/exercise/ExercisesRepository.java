package it.unimib.icasiduso.sportrack.data.repository.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.source.exercise.IExerciseDataSource;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.utils.NetworkUtil;

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

    private void saveExercisesInDatabase(List<Exercise> exercises) {
        exerciseLocalDataSource.saveExercises(exercises);
    }

    @Override
    public void getExercisesByMuscle(String muscle, GetExercisesCallback callback) {
        if (callback == null) return;
        exerciseLocalDataSource.getExercises(muscle);
        exerciseRemoteDataSource.fetchExercisesByMuscle(muscle);
    }

    @Override
    public void getExerciseById(long id) {
        exerciseLocalDataSource.getExercise(id);
    }

    @Override
    public void saveExercises(List<Exercise> exercises) {
        exerciseLocalDataSource.saveExercises(exercises);
    }

    @Override
    public void getExercisesByScheduleId(long scheduleId) {
    }


}
