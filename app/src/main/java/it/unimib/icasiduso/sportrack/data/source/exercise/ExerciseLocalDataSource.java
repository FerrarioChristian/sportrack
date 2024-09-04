package it.unimib.icasiduso.sportrack.data.source.exercise;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.database.ExerciseDao;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public class ExerciseLocalDataSource implements IExerciseDataSource.Local {
    private final ExerciseDao exerciseDao;

    public ExerciseLocalDataSource(ExerciseRoomDatabase exerciseRoomDatabase) {
        this.exerciseDao = exerciseRoomDatabase.exerciseDao();
    }

    @Override
    public void getExercises(String muscle, IExerciseRepository.GetExercisesCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Exercise> exercises = exerciseDao.getExercisesByMuscle(muscle);
            if (exercises == null || exercises.isEmpty()) {
                callback.onDataNotAvailable();
            } else {
                callback.onSuccess(exercises);
            }
        });
    }

    @Override
    public void getExercise(long id, IExerciseRepository.ExercisesCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> callback.onSuccess(exerciseDao.getExerciseById(id)));
    }

    @Override
    public void saveExercises(List<Exercise> exercises, IExerciseRepository.GetExercisesCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Exercise> dbExercises = exerciseDao.getAll();

            for (Exercise dbExercise : dbExercises) {
                if (exercises.contains(dbExercise)) {
                    exercises.set(exercises.indexOf(dbExercise), dbExercise);
                }
            }
            exerciseDao.insertExerciseList(exercises);


            callback.onSuccess(exerciseDao.getExercisesByMuscle(exercises.get(0).getMuscle()));
        });
    }
}
