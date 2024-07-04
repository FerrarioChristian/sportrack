package it.unimib.icasiduso.sportrack.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import it.unimib.icasiduso.sportrack.data.source.user.BaseAuthDataSource;
import it.unimib.icasiduso.sportrack.data.source.user.BaseUserDataSource;
import it.unimib.icasiduso.sportrack.model.Result;
import it.unimib.icasiduso.sportrack.model.User;

public class UserRepository implements IUserRepository, UserRepositoryCallbackable {
    private static final String TAG = UserRepository.class.getSimpleName();

    private final BaseAuthDataSource authDataSource;
    private final BaseUserDataSource userDataSource;

    private final MutableLiveData<Result> userMutableLiveData;

    public UserRepository(BaseAuthDataSource authDataSource, BaseUserDataSource userDataSource) {
        this.authDataSource = authDataSource;
        this.userMutableLiveData = new MutableLiveData<>();
        this.authDataSource.setUserRepositoryCallbackable(this);
        this.userDataSource = userDataSource;
        this.userDataSource.setUserRepositoryCallbackable(this);
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
        return null;
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
            userDataSource.saveUser(user);
        }
    }

    @Override
    public void onAuthFailure(String message) {

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

    }
}
