package it.unimib.icasiduso.sportrack.utils;


import android.app.Application;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.repository.exercise.ExercisesRepository;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExercisesRepository;
import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.data.repository.user.UserRepository;
import it.unimib.icasiduso.sportrack.data.service.ExercisesApiService;
import it.unimib.icasiduso.sportrack.data.service.NetworkService;
import it.unimib.icasiduso.sportrack.data.source.exercise.BaseExerciseLocalDataSource;
import it.unimib.icasiduso.sportrack.data.source.exercise.BaseExerciseRemoteDataSource;
import it.unimib.icasiduso.sportrack.data.source.exercise.ExerciseLocalDataSource;
import it.unimib.icasiduso.sportrack.data.source.exercise.ExerciseRemoteDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.AuthDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.BaseAuthDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.BaseUserRemoteDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.UserRemoteDataSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {
    private static volatile ServiceLocator INSTANCE = null;
    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if(INSTANCE == null) {
            synchronized (ServiceLocator.class) {
                if(INSTANCE == null) {
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

    public ExerciseRoomDatabase getExerciseDatabase(Application application){
        return ExerciseRoomDatabase.getDatabase(application);
    }

    public IUserRepository getUserRepository(Application application) {

        BaseAuthDataSource authDataSource = new AuthDataSource();
        BaseUserRemoteDataSource userRemoteDataSource = new UserRemoteDataSource();

        return new UserRepository(authDataSource, userRemoteDataSource);

    }

    public IExercisesRepository getExercisesRepository(Application application) {
        BaseExerciseRemoteDataSource exerciseRemoteDataSource;
        BaseExerciseLocalDataSource exerciseLocalDataSource;

        exerciseRemoteDataSource = new ExerciseRemoteDataSource(application.getString(R.string.api_key));
        exerciseLocalDataSource = new ExerciseLocalDataSource(getExerciseDatabase(application));

        return new ExercisesRepository(exerciseRemoteDataSource, exerciseLocalDataSource);
    }

    public NetworkService getNetworkService(Application application) {
        return new NetworkService(application.getApplicationContext());
    }

}
