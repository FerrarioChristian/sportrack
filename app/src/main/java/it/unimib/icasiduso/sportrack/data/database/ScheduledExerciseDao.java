package it.unimib.icasiduso.sportrack.data.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.ScheduledExercise;

@Dao
public interface ScheduledExerciseDao {

    @Insert
    long insertScheduledExercise(ScheduledExercise scheduledExercise);



}
