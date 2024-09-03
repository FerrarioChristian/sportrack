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
    public void getExercises(String muscle, IExerciseRepository.ExercisesCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> callback.onSuccess(exerciseDao.getExercisesByMuscle(muscle)));

    }

    @Override
    public void getExercise(long id, IExerciseRepository.ExercisesCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> callback.onSuccess(exerciseDao.getExerciseById(id)));
    }

    @Override
    public void saveExercises(List<Exercise> exercises, IExerciseRepository.ExercisesCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Exercise> dbExercises = exerciseDao.getAll();

            for (Exercise dbExercise : dbExercises) {
                if (exercises.contains(dbExercise)) {
                    exercises.set(exercises.indexOf(dbExercise), dbExercise);
                }
            }

            List<Long> insertedExerciseList = exerciseDao.insertExerciseList(exercises);
            for (int i = 0; i < exercises.size(); i++) {
                exercises.get(i).setExerciseId(insertedExerciseList.get(i));
            }
            callback.onSuccess(exercises);
        });
    }
}
