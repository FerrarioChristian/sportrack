package it.unimib.icasiduso.sportrack.model;

import java.util.List;

public class Exercises {

    private final List<Exercise> exercises;

    public Exercises(List<Exercise> exercises){
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Exercises{\n");
        for (Exercise exercise : exercises) {
            stringBuilder.append("\t").append(exercise.toString()).append("\n");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
