package it.unimib.icasiduso.sportrack.data.repository.exercise;

import android.app.Application;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.utils.JsonParserUtil;

public class ExerciseMockRepository implements IExerciseRepository {
    private static final String TAG = ExerciseMockRepository.class.getSimpleName();

    private final Application application;

    public ExerciseMockRepository(Application application) {
        this.application = application;
    }

    public void fetchExercises(String muscle) {
        JsonParserUtil jsonParserUtil = new JsonParserUtil(application);
        try {
            Exercise[] exercisesArray = jsonParserUtil.parseFromFileWithGSON(muscle + ".json");
            Log.d(TAG, Arrays.asList(exercisesArray).toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getExercisesByMuscle(String muscle, ExercisesCallback callback) {
    }

    @Override
    public void getExerciseById(long id, ExercisesCallback callback) {

    }

    @Override
    public void getExercisesByScheduleId(long scheduleId, ExercisesCallback callback) {

    }

    @Override
    public void saveExercises(List<Exercise> exercises, ExercisesCallback callback) {

    }

}
