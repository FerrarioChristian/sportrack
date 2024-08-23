package it.unimib.icasiduso.sportrack.utils;


import it.unimib.icasiduso.sportrack.App;
import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.repository.exercise.ExercisesRepository;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExercisesRepository;
import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.data.repository.user.UserRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.WorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.data.service.ExercisesApiService;
import it.unimib.icasiduso.sportrack.data.source.exercise.ExerciseLocalDataSource;
import it.unimib.icasiduso.sportrack.data.source.exercise.ExerciseRemoteDataSource;
import it.unimib.icasiduso.sportrack.data.source.exercise.IExerciseDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.AuthDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.BaseAuthDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.BaseUserRemoteDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.UserRemoteDataSource;
import it.unimib.icasiduso.sportrack.data.source.workout_exercise.IWorkoutExerciseDataSource;
import it.unimib.icasiduso.sportrack.data.source.workout_exercise.WorkoutExerciseLocalDataSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {
    private static volatile ServiceLocator INSTANCE = null;

    private ServiceLocator() {
    }

    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized (ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    public ExercisesApiService getExercisesApiService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(ExercisesApiService.class);
    }

    public ExerciseRoomDatabase getExerciseDatabase() {
        return ExerciseRoomDatabase.getDatabase();
    }

    public IUserRepository getUserRepository() {

        BaseAuthDataSource authDataSource = new AuthDataSource();
        BaseUserRemoteDataSource userRemoteDataSource = new UserRemoteDataSource();

        return new UserRepository(authDataSource, userRemoteDataSource);

    }

    public IExercisesRepository getExercisesRepository() {
        IExerciseDataSource.Remote exerciseRemoteDataSource;
        IExerciseDataSource.Local exerciseLocalDataSource;

        exerciseRemoteDataSource = new ExerciseRemoteDataSource(App.getRes().getString(R.string.api_key));
        exerciseLocalDataSource = new ExerciseLocalDataSource(getExerciseDatabase());

        return new ExercisesRepository(exerciseRemoteDataSource, exerciseLocalDataSource);
    }

    public IWorkoutExercisesRepository getWorkoutExercisesRepository() {
        //IWorkoutExerciseDataSource.Remote workoutExerciseRemoteDataSource = new WorkoutExerciseRemoteDataSource();
        IWorkoutExerciseDataSource.Local workoutExerciseLocalDataSource = new WorkoutExerciseLocalDataSource(getExerciseDatabase());

        return new WorkoutExercisesRepository(workoutExerciseLocalDataSource);
    }


}
