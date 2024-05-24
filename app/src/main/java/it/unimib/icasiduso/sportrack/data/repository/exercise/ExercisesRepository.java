package it.unimib.icasiduso.sportrack.data.repository.exercise;

import android.app.Application;
import android.util.Log;

import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.database.ExerciseDao;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.database.WorkoutExerciseDao;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.data.service.ExercisesApiService;
import it.unimib.icasiduso.sportrack.utils.NetworkUtil;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExercisesRepository implements IExercisesRepository {
    private static final String TAG = ExercisesRepository.class.getSimpleName();
    private final Application application;
    private final ExercisesApiService exercisesApiService;
    private final ExerciseDao exerciseDao;
    private final ExerciseRepositoryCallbackable exerciseRepositoryCallbackable;

    public ExercisesRepository(Application application, ExerciseRepositoryCallbackable responseCallback){
        this.application = application;
        this.exercisesApiService = ServiceLocator.getInstance().getExercisesApiService();
        ExerciseRoomDatabase exerciseRoomDatabase = ServiceLocator.getInstance().getExerciseDatabase(application);
        this.exerciseDao = exerciseRoomDatabase.exerciseDao();
        this.exerciseRepositoryCallbackable = responseCallback;
    }

    @Override
    public void fetchExercises(String muscle) {
        if(NetworkUtil.isNetworkAvailable(application)){
            fetchExercisesFromApi(muscle);
        } else {
            fetchExercisesFromDatabase(muscle);
        }
    }

    private void fetchExercisesFromApi(String muscle){
        Call<List<Exercise>> exercisesResponseCall = exercisesApiService.getExercises(muscle, application.getString(R.string.api_key));

        exercisesResponseCall.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                Log.d(TAG, response.toString());
                if(response.body() != null && response.isSuccessful()){
                    List<Exercise> exercises = response.body();
                    saveExercisesInDatabase(exercises);
                } else {
                    exerciseRepositoryCallbackable.onFailure("Error retrieving exercises");
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable throwable) {

            }
        });
    }

    public void fetchExercisesFromDatabase(String muscle){

        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            exerciseRepositoryCallbackable.onSuccess(exerciseDao.getExercisesByMuscle(muscle));
        });
    }

    private void saveExercisesInDatabase(List<Exercise> exercises) {
        ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Exercise> dbExercises = exerciseDao.getAll();

            for (Exercise dbExercise : dbExercises) {
                if (exercises.contains(dbExercise)) {
                    exercises.set(exercises.indexOf(dbExercise), dbExercise);
                }
            }

            List<Long> insertedExerciseList = exerciseDao.insertExerciseList(exercises);
            for (int i = 0; i < exercises.size(); i++) {
                exercises.get(i).setExerciseId(insertedExerciseList.get(i));
            }
            exerciseRepositoryCallbackable.onSuccess(exercises);
        });
    }

}
