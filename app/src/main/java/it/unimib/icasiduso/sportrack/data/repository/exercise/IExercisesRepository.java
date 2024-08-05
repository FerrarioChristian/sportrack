package it.unimib.icasiduso.sportrack.data.repository.exercise;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface IExercisesRepository {
    MutableLiveData<List<Exercise>> getExercisesByMuscle(String muscle);
    MutableLiveData<Exercise> getExerciseById(long id);
    void saveExercises(List<Exercise> exercises);
    MutableLiveData<List<Exercise>> getExercisesByScheduleId(long scheduleId);
}
