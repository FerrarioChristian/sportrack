package it.unimib.icasiduso.sportrack.utils;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import it.unimib.icasiduso.sportrack.model.Exercise;
import it.unimib.icasiduso.sportrack.model.Exercises;

public class JsonParserUtil {

    private static final String TAG = JsonParserUtil.class.getSimpleName();
    private final Context context;
    public JsonParserUtil(Application application){
        this.context = application.getApplicationContext();
    }

    public Exercises parseFromFileWithGSON(String file) throws IOException {
        InputStream inputStream = context.getAssets().open(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        Exercise[] exercisesArray = new Gson().fromJson(bufferedReader, Exercise[].class);
        List<Exercise> exercises = Arrays.asList(exercisesArray);

        return new Exercises(exercises);
    }
}
