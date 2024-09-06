package it.unimib.icasiduso.sportrack.utils;


import static it.unimib.icasiduso.sportrack.utils.Constants.FIREBASE_DATABASE;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.GsonBuilder;

import it.unimib.icasiduso.sportrack.App;
import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.repository.exercise.ExerciseRepository;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.data.repository.schedule.IScheduleRepository;
import it.unimib.icasiduso.sportrack.data.repository.schedule.ScheduleRepository;
import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.data.repository.user.UserRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.WorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.data.service.ExercisesApiService;
import it.unimib.icasiduso.sportrack.data.source.exercise.ExerciseLocalDataSource;
import it.unimib.icasiduso.sportrack.data.source.exercise.ExerciseRemoteDataSource;
import it.unimib.icasiduso.sportrack.data.source.exercise.IExerciseDataSource;
import it.unimib.icasiduso.sportrack.data.source.schedule.IScheduleDataSource;
import it.unimib.icasiduso.sportrack.data.source.schedule.ScheduleLocalDataSource;
import it.unimib.icasiduso.sportrack.data.source.schedule.ScheduleRemoteDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.AuthDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.IUserDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.UserRemoteDataSource;
import it.unimib.icasiduso.sportrack.data.source.workout_exercise.IWorkoutExerciseDataSource;
import it.unimib.icasiduso.sportrack.data.source.workout_exercise.WorkoutExerciseLocalDataSource;
import it.unimib.icasiduso.sportrack.data.source.workout_exercise.WorkoutExerciseRemoteDataSource;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(
                        Exercise.class,
                        new ExerciseDeserializer()).create()))
                .build();
        return retrofit.create(ExercisesApiService.class);
    }

    public ExerciseRoomDatabase getExerciseDatabase() {
        return ExerciseRoomDatabase.getDatabase();
    }

    public DatabaseReference getUserReference() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return FirebaseDatabase.getInstance().getReference(FIREBASE_DATABASE + user.getUid());
        } else {
            return null;
        }
    }

    public IUserRepository getUserRepository() {

        IUserDataSource.Auth authDataSource = new AuthDataSource();
        IUserDataSource.Remote userRemoteDataSource = new UserRemoteDataSource();

        return new UserRepository(authDataSource, userRemoteDataSource);

    }

    public IExerciseRepository getExercisesRepository() {
        IExerciseDataSource.Local exerciseLocalDataSource;
        IExerciseDataSource.Remote exerciseRemoteDataSource;

        exerciseLocalDataSource = new ExerciseLocalDataSource(getExerciseDatabase());
        exerciseRemoteDataSource = new ExerciseRemoteDataSource(App.getRes()
                .getString(R.string.api_key));

        return new ExerciseRepository(exerciseLocalDataSource, exerciseRemoteDataSource);
    }

    public IWorkoutExercisesRepository getWorkoutExercisesRepository() {
        IWorkoutExerciseDataSource.Local workoutExerciseLocalDataSource = new WorkoutExerciseLocalDataSource(
                getExerciseDatabase());
        IWorkoutExerciseDataSource.Remote workoutExerciseRemoteDataSource = new WorkoutExerciseRemoteDataSource();

        return new WorkoutExercisesRepository(workoutExerciseLocalDataSource,
                workoutExerciseRemoteDataSource);
    }

    public IScheduleRepository getScheduleRepository() {
        IScheduleDataSource.Local scheduleLocalDataSource = new ScheduleLocalDataSource(
                getExerciseDatabase());
        IScheduleDataSource.Remote scheduleRemoteDataSource = new ScheduleRemoteDataSource();

        return new ScheduleRepository(scheduleLocalDataSource, scheduleRemoteDataSource);
    }

}
