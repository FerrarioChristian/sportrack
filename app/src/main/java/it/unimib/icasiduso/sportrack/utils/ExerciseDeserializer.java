package it.unimib.icasiduso.sportrack.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public class ExerciseDeserializer implements JsonDeserializer<Exercise> {
    @Override
    public Exercise deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String type = jsonObject.get("type").getAsString();
        String muscle = jsonObject.get("muscle").getAsString();
        String equipment = jsonObject.get("equipment").getAsString();
        String difficulty = jsonObject.get("difficulty").getAsString();
        String instructions = jsonObject.get("instructions").getAsString();

        return new Exercise(name, type, muscle, equipment, difficulty, instructions);
    }
}