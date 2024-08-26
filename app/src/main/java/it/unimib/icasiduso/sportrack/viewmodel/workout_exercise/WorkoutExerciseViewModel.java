package it.unimib.icasiduso.sportrack.viewmodel.workout_exercise;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;


public class WorkoutExerciseViewModel extends ViewModel implements IWorkoutExercisesRepository.WorkoutExerciseCallback {
    private static final String TAG = WorkoutExerciseViewModel.class.getSimpleName();

    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<WorkoutExercise> workoutExerciseLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<WorkoutExercise>> workoutExercisesLiveData = new MutableLiveData<>();

    private final IWorkoutExercisesRepository workoutExerciseRepository;

    WorkoutExerciseViewModel(IWorkoutExercisesRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    public void setIsLoading(boolean isLoading) {
        if (isLoading) {
           isLoadingLiveData.postValue(true);
        } else {
           isLoadingLiveData.postValue(false);
        }
    }


    public MutableLiveData<List<WorkoutExercise>> getWorkoutExercisesByScheduleId(Long scheduleId) {
        workoutExerciseRepository.getWorkoutExercisesByScheduleId(scheduleId, this);
        return workoutExercisesLiveData;
    }

    public void addWorkoutExerciseToSchedule(WorkoutExercise workoutExercise) {
        setIsLoading(true);
        workoutExerciseRepository.addWorkoutExerciseToSchedule(workoutExercise, this);
    }

    public void deleteWorkoutExerciseFromSchedule(WorkoutExercise workoutExercise) {
        workoutExerciseRepository.deleteWorkoutExerciseFromSchedule(workoutExercise, this);
    }

    @Override
    public void onSuccess(List<WorkoutExercise> workoutExercises) {
        setIsLoading(false);
        workoutExercisesLiveData.postValue(workoutExercises);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(Exception exception) {
        setIsLoading(false);
        //TODO implementare eccezione
    }

    public MutableLiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public MutableLiveData<WorkoutExercise> getWorkoutExerciseLiveData() {
        return workoutExerciseLiveData;
    }

    public MutableLiveData<List<WorkoutExercise>> getWorkoutExercisesLiveData() {
        return workoutExercisesLiveData;
    }
}
