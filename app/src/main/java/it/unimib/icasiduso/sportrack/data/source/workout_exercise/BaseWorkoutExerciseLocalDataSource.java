package it.unimib.icasiduso.sportrack.data.source.workout_exercise;

import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public abstract class BaseWorkoutExerciseLocalDataSource {
    protected WorkoutExerciseCallback workoutExerciseCallback;

    public void setWorkoutExerciseCallback (WorkoutExerciseCallback workoutExerciseCallback) {
        this.workoutExerciseCallback = workoutExerciseCallback;
    }


    public abstract void addWorkoutExercise(WorkoutExercise workoutExercise);
    public abstract void deleteWorkoutExercise(WorkoutExercise workoutExercise);
    public abstract void getWorkoutExercisesByScheduleId(long scheduleId);
    public abstract void deleteWorkoutExercisesByScheduleId(long scheduleId);
}
