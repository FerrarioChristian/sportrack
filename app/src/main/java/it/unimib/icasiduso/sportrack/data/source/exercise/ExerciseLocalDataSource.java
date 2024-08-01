package it.unimib.icasiduso.sportrack.data.source.exercise;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.data.database.ExerciseDao;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public class ExerciseLocalDataSource extends BaseExerciseLocalDataSource{
    private final ExerciseDao exerciseDao;

    public ExerciseLocalDataSource(ExerciseRoomDatabase exerciseRoomDatabase) {
        this.exerciseDao = exerciseRoomDatabase.exerciseDao();
    }

    @Override
    public void getExercises(String muscle) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            exerciseCallback.onSuccessFromLocal(exerciseDao.getExercisesByMuscle(muscle));
        });

    }

    @Override
    public void getExercise(long id) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            ArrayList<Exercise> exercise = new ArrayList<>();
            exercise.add(exerciseDao.getExerciseById(id));
            exerciseCallback.onSuccessFromLocal(exercise);

        });
    }

    @Override
    public void saveExercises(List<Exercise> exercises) {
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
            exerciseCallback.onSuccessFromLocal(exercises);
        });
    }
}
