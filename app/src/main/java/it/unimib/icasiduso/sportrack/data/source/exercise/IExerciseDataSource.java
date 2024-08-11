package it.unimib.icasiduso.sportrack.data.source.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface IExerciseDataSource {
    interface Remote {
        void fetchExercisesByMuscle(String muscle);
        void saveExercises(List<Exercise> exercises);
    }

    interface Local {
        void getExercises(String muscle);
        void getExercise(long id);
        void saveExercises(List<Exercise> exercises);
    }
}
