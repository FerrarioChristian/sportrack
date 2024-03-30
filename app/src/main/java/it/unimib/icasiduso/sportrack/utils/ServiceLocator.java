package it.unimib.icasiduso.sportrack.utils;


import it.unimib.icasiduso.sportrack.service.ExercisesApiService;
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
}
