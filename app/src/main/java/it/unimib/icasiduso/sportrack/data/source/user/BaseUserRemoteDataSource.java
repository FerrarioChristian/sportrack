package it.unimib.icasiduso.sportrack.data.source.user;

import it.unimib.icasiduso.sportrack.model.User;

public abstract class BaseUserRemoteDataSource {
    protected UserCallback userCallback;

    public void setUserCallback(UserCallback userCallback) {
        this.userCallback = userCallback;
    }

    public abstract void saveUser(User user);
    //public abstract void getUserSchedules;

}
