package it.unimib.icasiduso.sportrack.data.repository.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface IExercisesRepository {

    void getExercisesByMuscle(String muscle, ExercisesCallback callback);

    void getExerciseById(long id, ExercisesCallback callback);

    void getExercisesByScheduleId(long scheduleId, ExercisesCallback callback);

    void saveExercises(List<Exercise> exercises, ExercisesCallback callback);

    interface ExercisesCallback {
        void onSuccess(Exercise exercise);
        void onSuccess(List<Exercise> exercises);
        void onFailure(Exception exception);
    }
}
