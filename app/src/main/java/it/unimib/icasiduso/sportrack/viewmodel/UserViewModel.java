package it.unimib.icasiduso.sportrack.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.model.Result;

public class UserViewModel extends ViewModel {
    private static final String TAG = UserViewModel.class.getSimpleName();

    private final IUserRepository userRepository;

    private MutableLiveData<Result> userMutableLiveData;

    public UserViewModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MutableLiveData<Result> getUserMutableLiveData(
            String email, String password, boolean isUserRegistered) {
        if (userMutableLiveData == null || userMutableLiveData.getValue() instanceof Result.Error) {
            getUserData(email, password, isUserRegistered);
        }
        return userMutableLiveData;
    }


    private void getUserData(String email, String password, boolean isUserRegistered) {
        userMutableLiveData = userRepository.getUser(email, password, isUserRegistered);
    }

    public MutableLiveData<Result> logout() {
        userMutableLiveData = userRepository.logout();
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
