package it.unimib.icasiduso.sportrack.data.source.user;

import static it.unimib.icasiduso.sportrack.utils.Constants.INVALID_CREDENTIALS_ERROR;
import static it.unimib.icasiduso.sportrack.utils.Constants.INVALID_USER_ERROR;
import static it.unimib.icasiduso.sportrack.utils.Constants.UNEXPECTED_ERROR;
import static it.unimib.icasiduso.sportrack.utils.Constants.USER_COLLISION_ERROR;
import static it.unimib.icasiduso.sportrack.utils.Constants.WEAK_PASSWORD_ERROR;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import it.unimib.icasiduso.sportrack.model.User;

public class AuthDataSource extends BaseAuthDataSource {

    private static final String TAG = AuthDataSource.class.getSimpleName();

    private final FirebaseAuth mAuth;

    public AuthDataSource() {
        this.mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public User getLoggedUser() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            return null;
        } else {
            return new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getUid());
        }
    }

    @Override
    public void logout() {
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    firebaseAuth.removeAuthStateListener(this);
                    userCallback.onSuccessLogout();
                }
            }
        };
        mAuth.addAuthStateListener(authStateListener);
        mAuth.signOut();
    }

    @Override
    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    userCallback.onAuthSuccess(
                            new User(firebaseUser.getDisplayName(), email, firebaseUser.getUid())
                    );
                } else {
                    userCallback.onAuthFailure(getErrorMessage(task.getException()));
                }
            } else {
                userCallback.onAuthFailure(getErrorMessage(task.getException()));
            }
        });

    }

    @Override
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    userCallback.onAuthSuccess(
                            new User(firebaseUser.getDisplayName(), email, firebaseUser.getUid())
                    );
                } else {
                    userCallback.onAuthFailure(getErrorMessage(task.getException()));
                }
            } else {
                userCallback.onAuthFailure(getErrorMessage(task.getException()));
            }
        });
    }

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
        return UNEXPECTED_ERROR;
    }
}
