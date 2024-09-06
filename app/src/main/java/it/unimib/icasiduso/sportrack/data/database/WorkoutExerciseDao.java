package it.unimib.icasiduso.sportrack.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

@Dao
public interface WorkoutExerciseDao {
    @Insert
    void insertAll(WorkoutExercise... workoutExercise);

    @Query("SELECT * FROM workoutexercise")
    List<WorkoutExercise> getAll();

    @Query("SELECT * FROM workoutexercise WHERE scheduleId = :scheduleId")
    List<WorkoutExercise> getWorkoutExercisesByScheduleId(Long scheduleId);

    @Delete
    void deleteWorkoutExercise(WorkoutExercise workoutExercise);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWorkoutExercises(List<WorkoutExercise> workoutExercise);

    @Query("DELETE FROM workoutexercise WHERE scheduleId = :scheduleId")
    void deleteWorkoutExercisesByScheduleId(long scheduleId);
}
