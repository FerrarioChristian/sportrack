package it.unimib.icasiduso.sportrack.data.repository.exercise;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.utils.JsonParserUtil;

public class ExercisesMockRepository implements IExercisesRepository{
    private static final String TAG = ExercisesMockRepository.class.getSimpleName();

    private final Application application;
    private final ExerciseRepositoryCallbackable responseCallback;

    public ExercisesMockRepository(Application application, ExerciseRepositoryCallbackable responseCallback){
        this.application = application;
        this.responseCallback = responseCallback;
    }

    public void fetchExercises(String muscle) {
        JsonParserUtil jsonParserUtil = new JsonParserUtil(application);
        try {
            Exercise[] exercisesArray = jsonParserUtil.parseFromFileWithGSON(muscle + ".json");
            Log.d(TAG, Arrays.asList(exercisesArray).toString());
            responseCallback.onSuccess(Arrays.asList(exercisesArray));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MutableLiveData<List<Exercise>> getExercisesByMuscle(String muscle) {
    return null;
    }

    @Override
    public void getExerciseById(long id) {

    }

    @Override
    public void saveExercises(List<Exercise> exercises) {

    }
}
