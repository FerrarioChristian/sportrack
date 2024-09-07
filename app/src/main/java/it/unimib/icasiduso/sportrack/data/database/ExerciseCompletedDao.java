package it.unimib.icasiduso.sportrack.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCompleted;

@Dao
public interface ExerciseCompletedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExerciseCompleted(ExerciseCompleted exerciseCompleted);

    @Query("SELECT * FROM ExerciseCompleted WHERE userId = :userId")
    void getExercisesCompleted(String userId);
}
