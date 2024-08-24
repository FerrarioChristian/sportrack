package it.unimib.icasiduso.sportrack.data.source.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.exercise.IExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface IExerciseDataSource {
    interface Remote {
        void fetchExercisesByMuscle(String muscle, IExercisesRepository.ExercisesCallback callback);

        void saveExercises(List<Exercise> exercises, IExercisesRepository.ExercisesCallback callback);
    }

    interface Local {
        void getExercises(String muscle, IExercisesRepository.ExercisesCallback callback);

        void getExercise(long id, IExercisesRepository.ExercisesCallback callback);

        void saveExercises(List<Exercise> exercises, IExercisesRepository.ExercisesCallback callback);
    }
}
