package it.unimib.icasiduso.sportrack.viewmodel.exercise;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.exercise.IExercisesRepository;
import it.unimib.icasiduso.sportrack.data.source.exercise.ExerciseCallback;
import it.unimib.icasiduso.sportrack.model.Result;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public class ExerciseViewModel extends ViewModel implements IExercisesRepository.GetExercisesCallback {
    private static final String TAG = ExerciseViewModel.class.getSimpleName();

    private MutableLiveData<Boolean> isLoadingLiveData;
    private MutableLiveData<Exercise> exerciseLiveData;
    private MutableLiveData<Result> exercisesLiveData;

    private final IExercisesRepository exercisesRepository;

    public ExerciseViewModel(IExercisesRepository exercisesRepository) {
        this.exercisesRepository = exercisesRepository;
    }

    public void setIsLoading(boolean isLoading) {
        if (isLoading) {
            isLoadingLiveData.postValue(true);
        } else {
            isLoadingLiveData.postValue(false);
        }
    }

    public MutableLiveData<Result> getExercisesByMuscle(String muscle) {
        exercisesRepository.getExercisesByMuscle(muscle, this);
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
    public void onSuccess(List<Exercise> exercises) {
        setIsLoading(false);
        exercisesLiveData.postValue(new Result.ExercisesResponseSuccess(exercises));
    }

    @Override
    public void onFailure(Exception exception) {
        setIsLoading(false);
        //TODO
    }

    public MutableLiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public MutableLiveData<Result> getExercisesLiveData() {
        return exercisesLiveData;
    }

    public MutableLiveData<Exercise> getExerciseLiveData() {
        return exerciseLiveData;
    }
}
