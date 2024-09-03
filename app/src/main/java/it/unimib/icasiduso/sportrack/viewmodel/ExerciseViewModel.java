package it.unimib.icasiduso.sportrack.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.model.Result;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public class ExerciseViewModel extends ViewModel implements IExerciseRepository.ExercisesCallback {
    private static final String TAG = ExerciseViewModel.class.getSimpleName();

    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Exercise> exerciseLiveData = new MutableLiveData<>();
    private final MutableLiveData<Result<List<Exercise>>> exercisesLiveData = new MutableLiveData<>();

    private final IExerciseRepository exercisesRepository;

    public ExerciseViewModel(IExerciseRepository exercisesRepository) {
        this.exercisesRepository = exercisesRepository;
    }

    public void setIsLoading(boolean isLoading) {
        if (isLoading) {
            isLoadingLiveData.postValue(true);
        } else {
            isLoadingLiveData.postValue(false);
        }
    }

    public MutableLiveData<Result<List<Exercise>>> getExercisesByMuscle(String muscle) {
        setIsLoading(true);
        exercisesRepository.getExercisesByMuscle(muscle, this);
        return exercisesLiveData;
    }

    public MutableLiveData<Exercise> getExerciseById(long id) {
        setIsLoading(true);
        final MutableLiveData<Exercise> result = new MutableLiveData<>();

        exercisesRepository.getExerciseById(id, new IExerciseRepository.ExercisesCallback() {
            @Override
            public void onSuccess(Exercise exercise) {
                setIsLoading(false);
                result.postValue(exercise);
            }

            @Override
            public void onSuccess(List<Exercise> exercises) {

            }

            @Override
            public void onFailure(Exception exception) {
                setIsLoading(false);
            }
        });
        return result;
    }

    @Override
    public void onSuccess(Exercise exercise) {
        setIsLoading(false);
        exerciseLiveData.postValue(exercise);
    }

    @Override
    public void onSuccess(List<Exercise> exercises) {
        setIsLoading(false);
        exercisesLiveData.postValue(new Result.Success<>(exercises));
    }

    @Override
    public void onFailure(Exception exception) {
        setIsLoading(false);
        //TODO
    }

    public MutableLiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public MutableLiveData<Result<List<Exercise>>> getExercisesLiveData() {
        return exercisesLiveData;
    }

    public MutableLiveData<Exercise> getExerciseLiveData() {
        return exerciseLiveData;
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final IExerciseRepository repository;

        public Factory(IExerciseRepository repository) {
            this.repository = repository;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ExerciseViewModel.class)) {
                return (T) new ExerciseViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }

    }
}
