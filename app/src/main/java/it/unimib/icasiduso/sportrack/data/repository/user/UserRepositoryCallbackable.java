package it.unimib.icasiduso.sportrack.data.repository.user;

import it.unimib.icasiduso.sportrack.model.User;

public interface UserRepositoryCallbackable {
    void onAuthSuccess(User user);
    void onAuthFailure(String message);

    void onDatabaseSuccess(User user);

    void onDatabaseFailure(String message);

    void onSuccessLogout();
}
