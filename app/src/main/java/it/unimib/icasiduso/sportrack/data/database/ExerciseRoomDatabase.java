package it.unimib.icasiduso.sportrack.data.database;

import static it.unimib.icasiduso.sportrack.utils.Constants.DATABASE_VERSION;
import static it.unimib.icasiduso.sportrack.utils.Constants.EXERCISE_DATABASE_NAME;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.ScheduledExercise;

@Database(entities = {Exercise.class, ScheduledExercise.class }, version = DATABASE_VERSION)
public abstract class ExerciseRoomDatabase extends RoomDatabase {

    public abstract ExerciseDao exerciseDao();
    public abstract ScheduledExerciseDao scheduledExerciseDao();

    private static volatile ExerciseRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ExerciseRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExerciseRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ExerciseRoomDatabase.class, EXERCISE_DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}
