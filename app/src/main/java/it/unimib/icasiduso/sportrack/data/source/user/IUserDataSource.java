package it.unimib.icasiduso.sportrack.data.source.user;

import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.model.User;

public interface IUserDataSource {
    interface Remote {
        void saveUser(User user, IUserRepository.UserDatabaseCallback callback);
    }

    interface Auth {

        void getLoggedUser(IUserRepository.UserAuthCallback callback);

        void logout(IUserRepository.UserLogoutCallback callback);

        void signUp(String email, String password, IUserRepository.UserAuthCallback callback);

        void signIn(String email, String password, IUserRepository.UserAuthCallback callback);

        void changePassword(String newPassword, IUserRepository.ChangePasswordCallback changePasswordCallback);
    }
}
