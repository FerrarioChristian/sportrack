package it.unimib.icasiduso.sportrack.data.repository.user;

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
        if (isUserRegistered) {
            signIn(email, password, callback);
        } else {
            signUp(email, password, callback);
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


    public void saveUser(User user, UserDatabaseCallback callback) {
        userRemoteDataSource.saveUser(user, callback);
    }

}
