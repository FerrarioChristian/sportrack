package it.unimib.icasiduso.sportrack.data.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public class WorkoutExerciseWithExercise {
    @Embedded
    public Exercise exercise;
    @Relation(parentColumn = "id", entityColumn = "exerciseId")
    public List<WorkoutExercise> workoutExercises;
}
