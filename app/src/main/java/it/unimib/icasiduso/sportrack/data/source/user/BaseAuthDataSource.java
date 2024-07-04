package it.unimib.icasiduso.sportrack.data.source.user;

import it.unimib.icasiduso.sportrack.data.repository.user.UserRepositoryCallbackable;
import it.unimib.icasiduso.sportrack.model.User;

public abstract class BaseAuthDataSource {
    protected UserRepositoryCallbackable userRepositoryCallbackable;

    public void setUserRepositoryCallbackable(UserRepositoryCallbackable userRepositoryCallbackable) {
        this.userRepositoryCallbackable = userRepositoryCallbackable;
    }

    public abstract User getLoggedUser();
    public abstract void logout();
    public abstract void signUp(String email, String password);
    public abstract void signIn(String email, String password);

}
