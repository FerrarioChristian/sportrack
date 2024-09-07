package it.unimib.icasiduso.sportrack.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCompleted;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;


public class WorkoutExerciseViewModel extends ViewModel {
    private static final String TAG = WorkoutExerciseViewModel.class.getSimpleName();

    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    //TODO Cambiare List<WorkoutExercise> con <Result> e gestire le eccezioni
    private final MutableLiveData<List<WorkoutExercise>> workoutExercisesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ExerciseCompleted>> exercisesCompletedLiveData  = new MutableLiveData<>();

    private final IWorkoutExercisesRepository workoutExerciseRepository;

    public WorkoutExerciseViewModel(IWorkoutExercisesRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    public void setIsLoading(boolean isLoading) {
        isLoadingLiveData.postValue(isLoading);
    }


    public MutableLiveData<List<WorkoutExercise>> getWorkoutExercises(Long scheduleId) {
        setIsLoading(true);
        workoutExerciseRepository.getWorkoutExercises(scheduleId,
                new IWorkoutExercisesRepository.GetWorkoutExerciseCallback() {

                    @Override
                    public void onWorkoutExercisesLoaded(List<WorkoutExercise> workoutExerciseList) {
                        setIsLoading(false);
                        workoutExercisesLiveData.postValue(workoutExerciseList);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        setIsLoading(false);
                        workoutExercisesLiveData.postValue(new ArrayList<>());
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        setIsLoading(false);
                    }
                });
        return workoutExercisesLiveData;
    }

    public void addWorkoutExercise(WorkoutExercise workoutExercise) {
        setIsLoading(true);
        workoutExerciseRepository.addWorkoutExercise(workoutExercise,
                new IWorkoutExercisesRepository.SaveWorkoutExerciseCallback() {

                    @Override
                    public void onSuccess() {
                        setIsLoading(false);
                        getWorkoutExercises(workoutExercise.getScheduleId());
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        setIsLoading(false);
                        //Toast.makeText(App.getInstance(), R.string.unexpected_error, Toast
                        // .LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteWorkoutExercise(int position) {
        setIsLoading(true);
        List<WorkoutExercise> workoutExercises = workoutExercisesLiveData.getValue();
        if (workoutExercises != null && position < workoutExercises.size()) {
            WorkoutExercise workoutExercise = workoutExercises.remove(position);
            workoutExerciseRepository.deleteWorkoutExercise(workoutExercise,
                    new IWorkoutExercisesRepository.SaveWorkoutExerciseCallback() {
                        @Override
                        public void onSuccess() {
                            setIsLoading(false);
                            workoutExercisesLiveData.postValue(workoutExercises);
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                        }
                    });
        }
    }

    public void saveExerciseCompleted(ExerciseCompleted exerciseCompleted) {
        workoutExerciseRepository.saveExerciseCompleted(exerciseCompleted);
    }

    public void getExercisesCompleted(String userId) {
        workoutExerciseRepository.getExercisesCompleted(userId, new IWorkoutExercisesRepository.GetExercisesCompletedCallback(){
            @Override
            public void onSuccess(List<ExerciseCompleted> exercisesCompleted) {
               exercisesCompletedLiveData.postValue(exercisesCompleted);
            }

            @Override
            public void onFailure(String errorMessage) {

            }

        });
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