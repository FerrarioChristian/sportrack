package it.unimib.icasiduso.sportrack.repository;

import it.unimib.icasiduso.sportrack.model.Exercise;
import it.unimib.icasiduso.sportrack.model.Exercises;

public interface ResponseCallback {
    void onSuccess(Exercise[] exercises);
    void onFailure(String errorMessage);

}
