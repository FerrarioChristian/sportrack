package it.unimib.icasiduso.sportrack.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

@Dao
public interface WorkoutExerciseDao {
    @Insert
    void insertAll(WorkoutExercise... workoutExercise);

    @Query("SELECT * FROM workoutexercise")
    List<WorkoutExercise> getAll();

    @Query("SELECT * FROM workoutexercise WHERE externalScheduleId = :scheduleId")
    List<WorkoutExercise> getWorkoutExerciseByScheduleId(Long scheduleId);

    @Delete
    void deleteWorkoutExercise(WorkoutExercise workoutExercise);

    @Upsert
    List<Long> insertWorkoutExercise(List<WorkoutExercise> workoutExercise);

    @Query("DELETE FROM workoutexercise WHERE externalScheduleId = :scheduleId")
    void deleteWorkoutExercisesByScheduleId(long scheduleId);
}
