package it.unimib.icasiduso.sportrack.data.repository.workout_exercise;

import android.app.Application;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.database.ExerciseDao;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.database.WorkoutExerciseDao;
import it.unimib.icasiduso.sportrack.data.repository.exercise.ExercisesRepository;
import it.unimib.icasiduso.sportrack.data.service.ExercisesApiService;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;

public class WorkoutExerciseRepository {
    private static final String TAG = ExercisesRepository.class.getSimpleName();
    private final Application application;
    private final ExercisesApiService exercisesApiService;
    private final ExerciseDao exerciseDao;
    private WorkoutExerciseDao workoutExerciseDao;
    private final WorkoutExerciseRepositoryCallbackable workoutExerciseRepositoryCallbackable;

    public WorkoutExerciseRepository(Application application, WorkoutExerciseRepositoryCallbackable responseCallback) {
        this.application = application;
        this.exercisesApiService = ServiceLocator.getInstance().getExercisesApiService();
        ExerciseRoomDatabase exerciseRoomDatabase = ServiceLocator.getInstance().getExerciseDatabase(application);
        this.exerciseDao = exerciseRoomDatabase.exerciseDao();
        this.workoutExerciseDao = exerciseRoomDatabase.workoutExerciseDao();
        this.workoutExerciseRepositoryCallbackable = responseCallback;
    }

    public void saveWorkoutExercise(WorkoutExercise workoutExercise) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.insertAll(workoutExercise);
            workoutExerciseRepositoryCallbackable.onSuccess();
        });
    }

    public void fetchWorkoutExercisesByScheduleId(Long scheduleId){
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseRepositoryCallbackable.onSuccess(workoutExerciseDao.getWorkoutExerciseByScheduleId(scheduleId));
        });
    }

    public void deleteWorkoutExercise(WorkoutExercise workoutExercise){
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.deleteWorkoutExercise(workoutExercise);
            workoutExerciseRepositoryCallbackable.onSuccess();
        });
    }

    public void deleteWorkoutExercisesByScheduleId(Long scheduleId){
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            workoutExerciseDao.deleteWorkoutExercisesByScheduleId(scheduleId);
            workoutExerciseRepositoryCallbackable.onSuccess();
        });
    }
}
