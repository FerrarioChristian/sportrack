package it.unimib.icasiduso.sportrack.viewmodel.workout_exercise;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;

public class WorkoutExerciseViewModelFactory implements ViewModelProvider.Factory{
    private final IWorkoutExercisesRepository iWorkoutExercisesRepository;

    public WorkoutExerciseViewModelFactory(IWorkoutExercisesRepository iWorkoutExercisesRepository) {
        this.iWorkoutExercisesRepository = iWorkoutExercisesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WorkoutExerciseViewModel(iWorkoutExercisesRepository);
    }
}