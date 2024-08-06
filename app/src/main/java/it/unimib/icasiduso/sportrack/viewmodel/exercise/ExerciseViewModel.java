package it.unimib.icasiduso.sportrack.viewmodel.exercise;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.exercise.IExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public class ExerciseViewModel extends ViewModel {
    private static final String TAG = ExerciseViewModel.class.getSimpleName();

    private MutableLiveData<Exercise> exercise;
    private MutableLiveData<List<Exercise>> exercises;

    private final IExercisesRepository exercisesRepository;
    //private final IWorkoutExercisesRepository workoutRepository;

    public ExerciseViewModel(IExercisesRepository exercisesRepository) {
        this.exercisesRepository = exercisesRepository;
    }

    public MutableLiveData<List<Exercise>> getExercisesByMuscle(String muscle) {
        if(muscle.isEmpty()) {
            //TODO creare string
            throw new IllegalArgumentException("invalid muscle parameter");
        }
        return exercisesRepository.getExercisesByMuscle(muscle);
    }

    public MutableLiveData<Exercise> getExerciseById(long id) {
        return null;
        //TODO: Implement
    }

    public MutableLiveData<List<Exercise>> getExercisesBySchedule(long scheduleId) {
        return null;
        //TODO: Implement
    }




}
