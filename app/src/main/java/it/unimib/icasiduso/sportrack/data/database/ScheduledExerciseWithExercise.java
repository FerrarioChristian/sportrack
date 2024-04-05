package it.unimib.icasiduso.sportrack.data.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.ScheduledExercise;

public class ScheduledExerciseWithExercise {
    @Embedded
    public Exercise exercise;
    @Relation(
            parentColumn = "exerciseId",
            entityColumn = "externalExerciseId"
    )
    public List<ScheduledExercise> scheduledExercises;
}
