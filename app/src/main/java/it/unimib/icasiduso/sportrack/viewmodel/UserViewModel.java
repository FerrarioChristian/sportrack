package it.unimib.icasiduso.sportrack.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.model.Result;
import it.unimib.icasiduso.sportrack.model.User;

public class UserViewModel extends ViewModel {
    private static final String TAG = UserViewModel.class.getSimpleName();

    private final IUserRepository userRepository;
    private final MutableLiveData<Result<User>> userMutableLiveData = new MutableLiveData<>();

    public UserViewModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Result<User>> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void getUserData(String email, String password, boolean isUserRegistered) {
        if (userMutableLiveData.getValue() instanceof Result.Error || userMutableLiveData.getValue() == null) {

            userRepository.getUser(email, password, isUserRegistered, new IUserRepository.UserAuthCallback() {

                @Override
                public void onAuthSuccess(User user) {
                    userMutableLiveData.postValue(new Result.Success<>(user));
                }

                @Override
                public void onAuthFailure(String message) {
                    userMutableLiveData.postValue(new Result.Error<>(message, null));
                }
            });
        }
    }

    public void clearUserMutableLiveData() {
        userMutableLiveData.postValue(null);
    }

    public MutableLiveData<Result<User>> logout() {
        userRepository.logout(() -> userMutableLiveData.postValue(null));
        return userMutableLiveData;
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final IUserRepository repository;

        public Factory(IUserRepository repository) {
            this.repository = repository;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(UserViewModel.class)) {
                return (T) new UserViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
