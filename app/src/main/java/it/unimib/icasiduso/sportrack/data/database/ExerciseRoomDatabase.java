package it.unimib.icasiduso.sportrack.data.database;

import static it.unimib.icasiduso.sportrack.utils.Constants.DATABASE_VERSION;
import static it.unimib.icasiduso.sportrack.utils.Constants.EXERCISE_DATABASE_NAME;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.icasiduso.sportrack.App;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCompleted;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

@Database(entities = {Exercise.class, WorkoutExercise.class, Schedule.class, ExerciseCompleted.class}, version = DATABASE_VERSION, exportSchema = false)
public abstract class ExerciseRoomDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(
            NUMBER_OF_THREADS);
    private static volatile ExerciseRoomDatabase INSTANCE;

    public static ExerciseRoomDatabase getDatabase() {
        if (INSTANCE == null) {
            synchronized (ExerciseRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(App.getInstance(),
                            ExerciseRoomDatabase.class,
                            EXERCISE_DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ExerciseDao exerciseDao();

    public abstract WorkoutExerciseDao workoutExerciseDao();

    public abstract ScheduleDao scheduleDao();

    public abstract ExerciseCompletedDao exerciseCompletedDao();
}
