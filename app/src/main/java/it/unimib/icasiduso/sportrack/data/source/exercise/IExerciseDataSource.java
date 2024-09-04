package it.unimib.icasiduso.sportrack.data.source.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface IExerciseDataSource {
    interface Remote {
        void fetchExercisesByMuscle(String muscle, IExerciseRepository.GetExercisesCallback callback);
    }

    interface Local {
        void getExercises(String muscle, IExerciseRepository.GetExercisesCallback callback);

        void getExercise(long id, IExerciseRepository.ExercisesCallback callback);

        void saveExercises(List<Exercise> exercises, IExerciseRepository.GetExercisesCallback callback);
    }
}
