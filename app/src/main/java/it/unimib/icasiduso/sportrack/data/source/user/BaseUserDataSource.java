package it.unimib.icasiduso.sportrack.data.source.user;

import it.unimib.icasiduso.sportrack.data.repository.user.UserRepositoryCallbackable;
import it.unimib.icasiduso.sportrack.model.User;

public abstract class BaseUserDataSource {
    protected UserRepositoryCallbackable userRepositoryCallbackable;

    public void setUserRepositoryCallbackable(UserRepositoryCallbackable userRepositoryCallbackable) {
        this.userRepositoryCallbackable = userRepositoryCallbackable;
    }

    public abstract void saveUser(User user);
    //public abstract void getUserSchedules;

}
