package it.unimib.icasiduso.sportrack.data.database;


import androidx.room.Dao;
import androidx.room.Insert;

import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

@Dao
public interface WorkoutExerciseDao {

    @Insert
    long insertWorkoutExercise(WorkoutExercise workoutExercise);



}
