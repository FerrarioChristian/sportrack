package it.unimib.icasiduso.sportrack.data.repository.user;

import it.unimib.icasiduso.sportrack.model.User;

public interface IUserRepository {

    void getUser(String email, String password, boolean isUserRegistered, UserAuthCallback callback);

    void logout(IUserRepository.UserLogoutCallback callback);

    void signUp(String email, String password, UserAuthCallback callback);

    void signIn(String email, String password, UserAuthCallback callback);

    interface UserAuthCallback {
        void onAuthSuccess(User user);

        void onAuthFailure(String message);

    }

    interface UserLogoutCallback {
        void onLogoutSuccess();
    }

    interface UserDatabaseCallback {
        void onDatabaseSuccess(User user);

        void onDatabaseFailure(String message);
    }

}
