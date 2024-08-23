package it.unimib.icasiduso.sportrack.data.source.user;

import it.unimib.icasiduso.sportrack.model.User;

public interface UserCallback {
    void onAuthSuccess(User user);

    void onAuthFailure(String message);

    void onDatabaseSuccess(User user);

    void onDatabaseFailure(String message);

    void onSuccessLogout();
}
