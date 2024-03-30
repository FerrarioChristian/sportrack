package it.unimib.icasiduso.sportrack.service;

import static it.unimib.icasiduso.sportrack.utils.Constants.EXERCISES_ENDPOINT;
import static it.unimib.icasiduso.sportrack.utils.Constants.EXERCISES_MUSCLE_PARAMETER;

import it.unimib.icasiduso.sportrack.model.Exercise;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ExercisesApiService {
    @GET(EXERCISES_ENDPOINT)
    Call<Exercise[]> getExercises(
            @Query(EXERCISES_MUSCLE_PARAMETER) String muscle,
            @Header("X-Api-Key") String apiKey
    );

}
