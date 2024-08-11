package it.unimib.icasiduso.sportrack.data.source.exercise;

import androidx.annotation.NonNull;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.service.ExercisesApiService;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseRemoteDataSource implements IExerciseDataSource.Remote {
    private static final String TAG = ExerciseRemoteDataSource.class.getSimpleName();
    private final ExercisesApiService exercisesApiService;
    private final String apiKey;

    public ExerciseRemoteDataSource(String apiKey) {
        this.exercisesApiService = ServiceLocator.getInstance().getExercisesApiService();
        this.apiKey = apiKey;
    }


    public void fetchExercisesByMuscle(String muscle) {
        Call<List<Exercise>> exercisesResponseCall = exercisesApiService.getExercises(muscle, apiKey);
        exercisesResponseCall.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(@NonNull Call<List<Exercise>> call, @NonNull Response<List<Exercise>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    exerciseCallback.onSuccessFromRemote(response.body());
                } else {
                    exerciseCallback.onFailureFromRemote(new Exception(response.message()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Exercise>> call, @NonNull Throwable throwable) {
                //TODO fix error message
                exerciseCallback.onFailureFromRemote(new Exception(throwable.getMessage()));
            }
        });
    }

    @Override
    public void saveExercises(List<Exercise> exercises) {

    }
}
