package it.unimib.icasiduso.sportrack.data.repository;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public interface ResponseCallback {
    void onSuccess(List<Exercise> exercises);
    void onFailure(String errorMessage);

}
