package it.unimib.icasiduso.sportrack.data.source.exercise;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.service.ExercisesApiService;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseRemoteDataSource extends BaseExerciseRemoteDataSource{
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
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    exerciseCallback.onSuccessFromRemote(response.body());
                } else {
                    exerciseCallback.onFailureFromRemote(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable throwable) {

            }
        });
    }
}
