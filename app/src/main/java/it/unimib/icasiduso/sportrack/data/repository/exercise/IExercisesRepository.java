package it.unimib.icasiduso.sportrack.data.repository.exercise;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.Result;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface IExercisesRepository {

    interface GetExercisesCallback {
        void onSuccess(List<Exercise> exercises);
        void onFailure(Exception exception);
    }

    void getExercisesByMuscle(String muscle, GetExercisesCallback callback);
    void getExerciseById(long id, GetExercisesCallback callback);
    void getExercisesByScheduleId(long scheduleId, GetExercisesCallback callback);
    void saveExercises(List<Exercise> exercises, GetExercisesCallback callback);

}
