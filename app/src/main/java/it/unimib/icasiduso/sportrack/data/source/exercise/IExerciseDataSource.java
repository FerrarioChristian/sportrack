package it.unimib.icasiduso.sportrack.data.source.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface IExerciseDataSource {
    interface Remote {
        void fetchExercisesByMuscle(String muscle, IExerciseRepository.ExercisesCallback callback);

        void saveExercises(List<Exercise> exercises, IExerciseRepository.ExercisesCallback callback);
    }

    interface Local {
        void getExercises(String muscle, IExerciseRepository.ExercisesCallback callback);

        void getExercise(long id, IExerciseRepository.ExercisesCallback callback);

        void saveExercises(List<Exercise> exercises, IExerciseRepository.ExercisesCallback callback);
    }
}
