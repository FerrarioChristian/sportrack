package it.unimib.icasiduso.sportrack.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import it.unimib.icasiduso.sportrack.data.source.user.BaseAuthDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.BaseUserRemoteDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.UserCallback;
import it.unimib.icasiduso.sportrack.model.Result;
import it.unimib.icasiduso.sportrack.model.User;

public class UserRepository implements IUserRepository, UserCallback {
    private static final String TAG = UserRepository.class.getSimpleName();

    private final BaseAuthDataSource authDataSource;
    private final BaseUserRemoteDataSource userRemoteDataSource;

    private final MutableLiveData<Result> userMutableLiveData;

    public UserRepository(BaseAuthDataSource authDataSource, BaseUserRemoteDataSource userRemoteDataSource) {
        this.authDataSource = authDataSource;
        this.userMutableLiveData = new MutableLiveData<>();
        this.authDataSource.setUserRepositoryCallbackable(this);
        this.userRemoteDataSource = userRemoteDataSource;
        this.userRemoteDataSource.setUserCallback(this);
    }


    @Override
    public MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered) {
        if (isUserRegistered) {
            signIn(email, password);
        } else {
            signUp(email, password);
        }
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getGoogleUser(String idToken) {
        return null;
    }

    @Override
    public MutableLiveData<Result> logout() {
        authDataSource.logout();
        return userMutableLiveData;
    }

    @Override
    public User getLoggedUser() {
        return null;
    }

    @Override
    public void signUp(String email, String password) {
        authDataSource.signUp(email, password);

    }

    @Override
    public void signIn(String email, String password) {
        authDataSource.signIn(email, password);
    }

    @Override
    public void onAuthSuccess(User user) {
        if (user != null) {
            userRemoteDataSource.saveUser(user);
        }
    }

    @Override
    public void onAuthFailure(String message) {
        userMutableLiveData.postValue(new Result.Error(message));
    }

    @Override
    public void onDatabaseSuccess(User user) {
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(user);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onDatabaseFailure(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessLogout() {
        userMutableLiveData.postValue(null);
    }
}
