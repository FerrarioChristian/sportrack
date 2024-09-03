package it.unimib.icasiduso.sportrack.data.source.user;

import static it.unimib.icasiduso.sportrack.utils.Constants.INVALID_CREDENTIALS_ERROR;
import static it.unimib.icasiduso.sportrack.utils.Constants.INVALID_USER_ERROR;
import static it.unimib.icasiduso.sportrack.utils.Constants.USER_COLLISION_ERROR;
import static it.unimib.icasiduso.sportrack.utils.Constants.WEAK_PASSWORD_ERROR;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import it.unimib.icasiduso.sportrack.App;
import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.model.User;

public class AuthDataSource implements IUserDataSource.Auth {

    private static final String TAG = AuthDataSource.class.getSimpleName();

    private final FirebaseAuth mAuth;

    public AuthDataSource() {
        this.mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void getLoggedUser(IUserRepository.UserAuthCallback callback) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            callback.onAuthFailure(App.getRes().getString(R.string.user_not_logged));
        } else {
            callback.onAuthSuccess(new User(user.getDisplayName(), user.getEmail(), user.getUid()));
        }
    }

    @Override
    public void logout(IUserRepository.UserLogoutCallback callback) {
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    firebaseAuth.removeAuthStateListener(this);
                    callback.onLogoutSuccess();
                }
            }
        };
        mAuth.addAuthStateListener(authStateListener);
        mAuth.signOut();
    }

    @Override
    public void signUp(String email, String password, IUserRepository.UserAuthCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    callback.onAuthSuccess(
                            new User(firebaseUser.getDisplayName(), email, firebaseUser.getUid())
                    );
                } else {
                    callback.onAuthFailure(getErrorMessage(task.getException()));
                }
            } else {
                callback.onAuthFailure(getErrorMessage(task.getException()));
            }
        });

    }

    @Override
    public void signIn(String email, String password, IUserRepository.UserAuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    callback.onAuthSuccess(
                            new User(firebaseUser.getDisplayName(), email, firebaseUser.getUid())
                    );
                } else {
                    callback.onAuthFailure(getErrorMessage(task.getException()));
                }
            } else {
                callback.onAuthFailure(getErrorMessage(task.getException()));
            }
        });
    }

    //TODO sistemare i messaggi d'errore
    private String getErrorMessage(Exception exception) {
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            return WEAK_PASSWORD_ERROR;
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            return INVALID_CREDENTIALS_ERROR;
        } else if (exception instanceof FirebaseAuthInvalidUserException) {
            return INVALID_USER_ERROR;
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            return USER_COLLISION_ERROR;
        }
        return App.getRes().getString(R.string.unexpected_error);
    }
}
