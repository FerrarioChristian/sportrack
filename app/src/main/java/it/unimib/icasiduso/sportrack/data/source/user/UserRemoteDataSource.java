package it.unimib.icasiduso.sportrack.data.source.user;

import static it.unimib.icasiduso.sportrack.utils.Constants.FIREBASE_DATABASE;
import static it.unimib.icasiduso.sportrack.utils.Constants.FIREBASE_USERS_COLLECTION;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.unimib.icasiduso.sportrack.model.User;

public class UserRemoteDataSource extends BaseUserRemoteDataSource{
    private static final String TAG =  UserRemoteDataSource.class.getSimpleName();
    private final DatabaseReference databaseReference;

    public UserRemoteDataSource() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_DATABASE);
        databaseReference = firebaseDatabase.getReference().getRef();
    }

    @Override
    public void saveUser(User user) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(user.getIdToken()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userCallback.onDatabaseSuccess(user);
                } else {
                    databaseReference.child(FIREBASE_USERS_COLLECTION).child(user.getIdToken()).setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    userCallback.onDatabaseSuccess(user);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    userCallback.onDatabaseFailure(e.getLocalizedMessage());
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                userCallback.onDatabaseFailure(error.getMessage());
            }
        });
    }

}
