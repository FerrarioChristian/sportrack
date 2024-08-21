package it.unimib.icasiduso.sportrack.data.source.exercise;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.data.database.ExerciseDao;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public class ExerciseLocalDataSource implements IExerciseDataSource.Local {
    private final ExerciseDao exerciseDao;

    public ExerciseLocalDataSource(ExerciseRoomDatabase exerciseRoomDatabase) {
        this.exerciseDao = exerciseRoomDatabase.exerciseDao();
    }

    @Override
    public void getExercises(String muscle, IExercisesRepository.GetExercisesCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            callback.onSuccess(exerciseDao.getExercisesByMuscle(muscle));
        });

    }

    @Override
    public void getExercise(long id, IExercisesRepository.GetExercisesCallback callback) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            ArrayList<Exercise> exercise = new ArrayList<>();
            exercise.add(exerciseDao.getExerciseById(id));
            callback.onSuccess(exercise);

        });
    }

    @Override
    public void saveExercises(List<Exercise> exercises, IExercisesRepository.GetExercisesCallback callback) {
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
