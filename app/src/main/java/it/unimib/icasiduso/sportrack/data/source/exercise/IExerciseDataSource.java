package it.unimib.icasiduso.sportrack.data.source.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.exercise.IExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface IExerciseDataSource {
    interface Remote {
        void fetchExercisesByMuscle(String muscle, IExercisesRepository.GetExercisesCallback callback);
        void saveExercises(List<Exercise> exercises, IExercisesRepository.GetExercisesCallback callback);
    }

    interface Local {
        void getExercises(String muscle, IExercisesRepository.GetExercisesCallback callback);
        void getExercise(long id, IExercisesRepository.GetExercisesCallback callback);
        void saveExercises(List<Exercise> exercises, IExercisesRepository.GetExercisesCallback callback);
    }
}
