package it.unimib.icasiduso.sportrack.data.repository.workout_exercise;

import android.app.Application;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.database.ExerciseDao;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.database.WorkoutExerciseDao;
import it.unimib.icasiduso.sportrack.data.repository.exercise.ExercisesRepository;
import it.unimib.icasiduso.sportrack.data.service.ExercisesApiService;
import it.unimib.icasiduso.sportrack.data.source.workout_exercise.IWorkoutExerciseDataSource;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;

public class WorkoutExerciseRepository implements IWorkoutExercisesRepository{

    private static final String TAG = ExercisesRepository.class.getSimpleName();

    private final IWorkoutExerciseDataSource.Local workoutExerciseLocalDataSource;
    private final IWorkoutExerciseDataSource.Remote workoutExerciseRemoteDataSource;


    public WorkoutExerciseRepository(
            IWorkoutExerciseDataSource.Remote workoutExerciseRemoteDataSource,
            IWorkoutExerciseDataSource.Local workoutExerciseLocalDataSource
    ) {
        this.workoutExerciseRemoteDataSource = workoutExerciseRemoteDataSource;
        this.workoutExerciseLocalDataSource = workoutExerciseLocalDataSource;
    }


    @Override
    public void addWorkoutExerciseToSchedule(WorkoutExercise workoutExercise) {

    }

    @Override
    public void deleteWorkoutExerciseFromSchedule(WorkoutExercise workoutExercise) {

    }

    @Override
    public void getWorkoutExerciseByScheduleId(Long scheduleId) {

    }
}

