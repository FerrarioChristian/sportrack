package it.unimib.icasiduso.sportrack.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    List<Exercise> getAll();

    @Query("SELECT * FROM exercise WHERE muscle = :muscle")
    List<Exercise> getExercisesByMuscle(String muscle);

    @Query("SELECT id FROM exercise WHERE name = :name")
    long getExerciseIdByName(String name);

    @Query("SELECT * FROM exercise WHERE id = :id")
    Exercise getExerciseById(long id);


    @Insert
    void insertAll(Exercise... exercise);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExerciseList(List<Exercise> exerciseList);


    @Transaction
    @Query("SELECT * FROM Exercise")
    List<WorkoutExerciseWithExercise> getWorkoutExerciseWithExercise();


    @Delete
    void delete(Exercise exercise);

}
