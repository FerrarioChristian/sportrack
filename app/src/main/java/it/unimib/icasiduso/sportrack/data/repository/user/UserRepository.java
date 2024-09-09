package it.unimib.icasiduso.sportrack.data.repository.user;

import android.widget.Toast;

import it.unimib.icasiduso.sportrack.App;
import it.unimib.icasiduso.sportrack.data.source.user.IUserDataSource;
import it.unimib.icasiduso.sportrack.model.User;

public class UserRepository implements IUserRepository {

    private static final String TAG = UserRepository.class.getSimpleName();

    private final IUserDataSource.Auth authDataSource;
    private final IUserDataSource.Remote userRemoteDataSource;

    public UserRepository(IUserDataSource.Auth authDataSource, IUserDataSource.Remote userRemoteDataSource) {
        this.authDataSource = authDataSource;
        this.userRemoteDataSource = userRemoteDataSource;
    }


    @Override
    public void getUser(String email, String password, boolean isUserRegistered, UserAuthCallback callback) {

        UserAuthCallback commonCallback = new UserAuthCallback() {
            @Override
            public void onAuthSuccess(User user) {
                saveUser(user, new UserDatabaseCallback() {
                    @Override
                    public void onDatabaseSuccess(User user) {
                        callback.onAuthSuccess(user);
                    }

                    @Override
                    public void onDatabaseFailure(String message) {
                        callback.onAuthSuccess(user);
                        Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onAuthFailure(String message) {
                callback.onAuthFailure(message);
            }
        };

        if (isUserRegistered) {
            signIn(email, password, commonCallback);
        } else {
            signUp(email, password, commonCallback);
        }
    }

    @Override
    public void logout(IUserRepository.UserLogoutCallback callback) {
        authDataSource.logout(callback);
    }

    @Override
    public void signUp(String email, String password, UserAuthCallback callback) {
        authDataSource.signUp(email, password, callback);
    }

    @Override
    public void signIn(String email, String password, UserAuthCallback callback) {
        authDataSource.signIn(email, password, callback);
    }

    @Override
    public void changePassword(String newPassword, ChangePasswordCallback callback) {
        authDataSource.changePassword(newPassword, callback);
    }


    public void saveUser(User user, UserDatabaseCallback callback) {
        userRemoteDataSource.saveUser(user, callback);
    }

}
