package it.unimib.icasiduso.sportrack.data.service;

import static it.unimib.icasiduso.sportrack.utils.Constants.EXERCISES_ENDPOINT;
import static it.unimib.icasiduso.sportrack.utils.Constants.EXERCISES_MUSCLE_PARAMETER;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ExercisesApiService {
    @GET(EXERCISES_ENDPOINT)
    Call<List<Exercise>> getExercises(
            @Query(EXERCISES_MUSCLE_PARAMETER) String muscle,
            @Header("X-Api-Key") String apiKey
    );

}
