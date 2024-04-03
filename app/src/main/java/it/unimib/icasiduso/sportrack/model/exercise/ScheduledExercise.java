package it.unimib.icasiduso.sportrack.model.exercise;

import android.annotation.SuppressLint;

@SuppressLint("ParcelCreator")
public class ScheduledExercise extends Exercise {
    private int series;
    private int repetitions;
    private int weight;

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}