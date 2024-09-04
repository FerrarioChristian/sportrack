package it.unimib.icasiduso.sportrack.data.repository.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface IExerciseRepository {

    void getExercisesByMuscle(String muscle, GetExercisesCallback callback);

    void getExerciseById(long id, ExercisesCallback callback);

    void saveExercises(List<Exercise> exercises, GetExercisesCallback callback);

    interface GetExercisesCallback {
        void onSuccess(List<Exercise> exercises);
        void onDataNotAvailable();
        void onFailure(Exception exception);
    }


    interface ExercisesCallback {
        void onSuccess(Exercise exercise);
        void onSuccess(List<Exercise> exercises);
        void onFailure(Exception exception);
    }
}
