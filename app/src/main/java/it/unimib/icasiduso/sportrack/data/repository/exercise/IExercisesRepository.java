package it.unimib.icasiduso.sportrack.data.repository.exercise;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.Result;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface IExercisesRepository {
    void getExercisesByMuscle(String muscle);
    void getExerciseById(long id);
    void saveExercises(List<Exercise> exercises);
    void getExercisesByScheduleId(long scheduleId);
}
