package it.unimib.icasiduso.sportrack.viewmodel.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
        if (userMutableLiveData == null) {
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
}
