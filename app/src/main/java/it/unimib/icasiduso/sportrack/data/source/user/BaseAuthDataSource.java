package it.unimib.icasiduso.sportrack.data.source.user;

import it.unimib.icasiduso.sportrack.model.User;

public abstract class BaseAuthDataSource {
    protected UserCallback userCallback;

    public void setUserRepositoryCallbackable(UserCallback userCallback) {
        this.userCallback = userCallback;
    }

    public abstract User getLoggedUser();

    public abstract void logout();

    public abstract void signUp(String email, String password);

    public abstract void signIn(String email, String password);

}
