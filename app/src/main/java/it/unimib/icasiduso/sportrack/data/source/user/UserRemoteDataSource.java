package it.unimib.icasiduso.sportrack.data.source.user;

import static it.unimib.icasiduso.sportrack.utils.Constants.FIREBASE_DATABASE;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.model.User;

public class UserRemoteDataSource implements IUserDataSource.Remote {
    private static final String TAG = UserRemoteDataSource.class.getSimpleName();
    private final DatabaseReference databaseReference;

    public UserRemoteDataSource() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(FIREBASE_DATABASE);
    }

    @Override
    public void saveUser(User user, IUserRepository.UserDatabaseCallback callback) {
        databaseReference.child(user.getIdToken()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    callback.onDatabaseSuccess(user);
                } else {
                    databaseReference.child(user.getIdToken()).setValue(user)
                            .addOnSuccessListener(aVoid -> callback.onDatabaseSuccess(user))
                            .addOnFailureListener(e -> callback.onDatabaseFailure(e.getLocalizedMessage()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDatabaseFailure(error.getMessage());
            }
        });
    }

}
