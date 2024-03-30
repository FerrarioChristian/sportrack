package it.unimib.icasiduso.sportrack.repository;

import android.app.Application;
import android.util.Log;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.model.Exercise;
import it.unimib.icasiduso.sportrack.service.ExercisesApiService;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExercisesRepository implements IExercisesRepository {
    private static final String TAG = ExercisesRepository.class.getSimpleName();
    private final Application application;
    private final ExercisesApiService exercisesApiService;
    private final ResponseCallback responseCallback;

    public ExercisesRepository(Application application, ResponseCallback responseCallback){
        this.application = application;
        this.exercisesApiService = ServiceLocator.getInstance().getExercisesApiService();
        this.responseCallback = responseCallback;
    }



    @Override
    public void fetchExercises(String muscle) {
        Call<Exercise[]> exercisesResponseCall = exercisesApiService.getExercises(muscle, application.getString(R.string.api_key));

        exercisesResponseCall.enqueue(new Callback<Exercise[]>() {
            @Override
            public void onResponse(Call<Exercise[]> call, Response<Exercise[]> response) {
                Log.d(TAG, response.toString());
                if(response.body() != null && response.isSuccessful()){
                    Exercise[] exercises = response.body();
                    responseCallback.onSuccess(exercises);
                } else {
                    responseCallback.onFailure("Error retrieving exercises");
                }
            }

            @Override
            public void onFailure(Call<Exercise[]> call, Throwable throwable) {

            }
        });

    }
}
