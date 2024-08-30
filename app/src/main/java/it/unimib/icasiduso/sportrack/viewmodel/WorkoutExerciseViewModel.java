package it.unimib.icasiduso.sportrack.viewmodel;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import it.unimib.icasiduso.sportrack.App;
import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;


public class WorkoutExerciseViewModel extends ViewModel implements IWorkoutExercisesRepository.WorkoutExerciseCallback {
    private static final String TAG = WorkoutExerciseViewModel.class.getSimpleName();

    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<WorkoutExercise>> workoutExercisesLiveData = new MutableLiveData<>();

    private final IWorkoutExercisesRepository workoutExerciseRepository;

    public WorkoutExerciseViewModel(IWorkoutExercisesRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    public void setIsLoading(boolean isLoading) {
           isLoadingLiveData.postValue(isLoading);
    }


    public MutableLiveData<List<WorkoutExercise>> getWorkoutExercisesByScheduleId(Long scheduleId) {
        setIsLoading(true);
        workoutExerciseRepository.getWorkoutExercisesByScheduleId(scheduleId, this);
        return workoutExercisesLiveData;
    }

    public void addWorkoutExerciseToSchedule(WorkoutExercise workoutExercise) {
        setIsLoading(true);
        workoutExerciseRepository.addWorkoutExerciseToSchedule(workoutExercise, this);
    }

    public void deleteWorkoutExerciseFromSchedule(int position) {
        List<WorkoutExercise> workoutExercises = workoutExercisesLiveData.getValue();
        if (workoutExercises != null && position < workoutExercises.size()){
            WorkoutExercise workoutExercise = workoutExercises.remove(position);
            workoutExerciseRepository.deleteWorkoutExerciseFromSchedule(workoutExercise, new IWorkoutExercisesRepository.WorkoutExerciseCallback() {
                @Override
                public void onSuccess(List<WorkoutExercise> workoutExercises) {
                }

                @Override
                public void onSuccess() {
                    workoutExercisesLiveData.postValue(workoutExercises);
                }

                @Override
                public void onFailure(Exception exception) {
                }
            });
        }
    }

    @Override
    public void onSuccess(List<WorkoutExercise> workoutExercises) {
        setIsLoading(false);
        workoutExercisesLiveData.postValue(workoutExercises);
    }

    @Override
    public void onSuccess() {
        setIsLoading(false);
    }

    @Override
    public void onFailure(Exception exception) {
        setIsLoading(false);
        Toast.makeText(App.getInstance(), R.string.unexpected_error, Toast.LENGTH_SHORT).show();
    }

    public MutableLiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }


    public static class Factory implements ViewModelProvider.Factory {
        private final IWorkoutExercisesRepository repository;

        public Factory(IWorkoutExercisesRepository repository) {
            this.repository = repository;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(WorkoutExerciseViewModel.class)) {
                return (T) new WorkoutExerciseViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}