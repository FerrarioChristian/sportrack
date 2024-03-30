package it.unimib.icasiduso.sportrack.repository;

import android.app.Application;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

import it.unimib.icasiduso.sportrack.model.Exercise;
import it.unimib.icasiduso.sportrack.utils.JsonParserUtil;

public class ExercisesMockRepository implements IExercisesRepository{
    private static final String TAG = ExercisesMockRepository.class.getSimpleName();

    private final Application application;
    private final ResponseCallback responseCallback;

    public ExercisesMockRepository(Application application, ResponseCallback responseCallback){
        this.application = application;
        this.responseCallback = responseCallback;
    }

    public void fetchExercises(String muscle) {
        JsonParserUtil jsonParserUtil = new JsonParserUtil(application);
        try {
            Exercise[] exercises = jsonParserUtil.parseFromFileWithGSON(muscle + ".json");
            Log.d(TAG, Arrays.asList(exercises).toString());
            responseCallback.onSuccess(exercises);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
