package it.unimib.icasiduso.sportrack.viewmodel.exercise;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.icasiduso.sportrack.data.repository.exercise.IExercisesRepository;

public class ExerciseViewModelFactory implements ViewModelProvider.Factory {
    private final IExercisesRepository iExercisesRepository;

    public ExerciseViewModelFactory(IExercisesRepository iExercisesRepository) {
        this.iExercisesRepository = iExercisesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ExerciseViewModel(iExercisesRepository);
    }

}
