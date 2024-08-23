package it.unimib.icasiduso.sportrack.viewmodel.workout_exercise;

import androidx.lifecycle.ViewModel;

import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;


public class WorkoutExerciseViewModel extends ViewModel {
    private static final String TAG = WorkoutExerciseViewModel.class.getSimpleName();
    private final IWorkoutExercisesRepository workoutExerciseRepository;

    WorkoutExerciseViewModel(IWorkoutExercisesRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    public void addWorkoutExerciseToSchedule(WorkoutExercise workoutExercise) {
        workoutExerciseRepository.addWorkoutExerciseToSchedule(workoutExercise);
    }

    public void deleteWorkoutExerciseFromSchedule(WorkoutExercise workoutExercise) {
        workoutExerciseRepository.deleteWorkoutExerciseFromSchedule(workoutExercise);
    }
}
