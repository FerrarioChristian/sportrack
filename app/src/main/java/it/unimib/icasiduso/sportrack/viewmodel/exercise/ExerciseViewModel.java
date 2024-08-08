package it.unimib.icasiduso.sportrack.viewmodel.exercise;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.exercise.IExercisesRepository;
import it.unimib.icasiduso.sportrack.data.source.exercise.ExerciseCallback;
import it.unimib.icasiduso.sportrack.model.Result;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public class ExerciseViewModel extends ViewModel implements ExerciseCallback {
    private static final String TAG = ExerciseViewModel.class.getSimpleName();

    private MutableLiveData<Exercise> exerciseLiveData;
    private MutableLiveData<Result> exercisesLiveData;

    private final IExercisesRepository exercisesRepository;
    //private final IWorkoutExercisesRepository workoutRepository;

    public ExerciseViewModel(IExercisesRepository exercisesRepository) {
        this.exercisesRepository = exercisesRepository;
    }

    public MutableLiveData<Result> getExercisesByMuscle(String muscle) throws IllegalArgumentException {
        if(muscle.isEmpty()) {
            //TODO creare string
            throw new IllegalArgumentException("invalid muscle parameter");
        }
        exercisesRepository.getExercisesByMuscle(muscle);
        return exercisesLiveData;
    }

    public MutableLiveData<Exercise> getExerciseById(long id) {
        return null;
        //TODO: Implement
    }

    public MutableLiveData<List<Exercise>> getExercisesBySchedule(long scheduleId) {
        return null;
        //TODO: Implement
    }

    @Override
    public void onSuccessFromRemote(List<Exercise> exercises) {
        Result.ExercisesResponseSuccess result = new Result.ExercisesResponseSuccess(exercises);
        this.exercisesLiveData.postValue(result);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error error = new Result.Error(exception.getMessage());
        this.exercisesLiveData.postValue(error);
    }

    @Override
    public void onSuccessFromLocal(List<Exercise> exercises) {
        Result.ExercisesResponseSuccess result = new Result.ExercisesResponseSuccess(exercises);
        this.exercisesLiveData.postValue(result);
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error error = new Result.Error(exception.getMessage());
        this.exercisesLiveData.postValue(error);
    }
}
