package it.unimib.icasiduso.sportrack.utils;


import android.app.Application;

import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.data.repository.user.UserRepository;
import it.unimib.icasiduso.sportrack.data.service.ExercisesApiService;
import it.unimib.icasiduso.sportrack.data.source.user.AuthDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.BaseAuthDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.BaseUserDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.UserDataSource;
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
        BaseUserDataSource userDataSource = new UserDataSource();

        return new UserRepository(authDataSource, userDataSource);

    }
}
