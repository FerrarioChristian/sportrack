package it.unimib.icasiduso.sportrack.data.repository.exercise;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.database.ExerciseDao;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.service.NetworkService;
import it.unimib.icasiduso.sportrack.data.source.exercise.BaseExerciseLocalDataSource;
import it.unimib.icasiduso.sportrack.data.source.exercise.BaseExerciseRemoteDataSource;
import it.unimib.icasiduso.sportrack.data.source.exercise.ExerciseCallback;
import it.unimib.icasiduso.sportrack.model.Result;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.data.service.ExercisesApiService;
import it.unimib.icasiduso.sportrack.utils.NetworkUtil;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExercisesRepository implements IExercisesRepository, ExerciseCallback {
    private NetworkService networkService;
    private static final String TAG = ExercisesRepository.class.getSimpleName();
    private final BaseExerciseLocalDataSource exerciseLocalDataSource;
    private final BaseExerciseRemoteDataSource exerciseRemoteDataSource;

    private MutableLiveData<Result> exercises;

    public ExercisesRepository(BaseExerciseRemoteDataSource exerciseRemoteDataSource, BaseExerciseLocalDataSource exerciseLocalDataSource) {
        this.exerciseRemoteDataSource = exerciseRemoteDataSource;
        this.exerciseLocalDataSource = exerciseLocalDataSource;
        this.networkService = ServiceLocator.getInstance().getNetworkService();
        exercises = new MutableLiveData<>();
    }

    private MutableLiveData<List<Exercise>> fetchExercisesFromApi(String muscle) {
        exerciseRemoteDataSource.fetchExercisesByMuscle(muscle);
        return exercises;
    }

    public MutableLiveData<List<Exercise>> fetchExercisesFromDatabase(String muscle) {
        exerciseLocalDataSource.getExercises(muscle);
        return exercises;
    }

    private void saveExercisesInDatabase(List<Exercise> exercises) {
        exerciseLocalDataSource.saveExercises(exercises);
    }

    @Override
    public MutableLiveData<Result> getExercisesByMuscle(String muscle) {
        exerciseRemoteDataSource.fetchExercisesByMuscle(muscle);

        exerciseLocalDataSource.getExercises(muscle);

        return exercises;

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
    public MutableLiveData<List<Exercise>> getExercisesByScheduleId(long scheduleId) {
        return null;
    }


    @Override
    public void onSuccessFromRemote(List<Exercise> exercises) {

    }

    @Override
    public void onFailureFromRemote(Exception exception) {

    }

    @Override
    public void onSuccessFromLocal(List<Exercise> exercises) {

    }

    @Override
    public void onFailureFromLocal(Exception exception) {

    }
}
