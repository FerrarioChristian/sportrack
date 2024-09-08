package it.unimib.icasiduso.sportrack.data.source.workout_exercise;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;

public class WorkoutExerciseRemoteDataSource implements IWorkoutExerciseDataSource.Remote {

    private static final String TAG = WorkoutExerciseRemoteDataSource.class.getSimpleName();

    private final DatabaseReference databaseReference;

    public WorkoutExerciseRemoteDataSource() {
        this.databaseReference = ServiceLocator.getInstance().getUserReference();
    }


    @Override
    public void addWorkoutExercise(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.SaveWorkoutExerciseCallback callback) {
        databaseReference.child("schedules")
                .child(String.valueOf(workoutExercise.getScheduleId()))
                .child("workoutExercises")
                .child(String.valueOf(workoutExercise.getId()))
                .setValue(workoutExercise)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void deleteWorkoutExercise(WorkoutExercise workoutExercise, IWorkoutExercisesRepository.SaveWorkoutExerciseCallback callback) {
        databaseReference.child("schedules")
                .child(String.valueOf(workoutExercise.getScheduleId()))
                .child("workoutExercises")
                .child(String.valueOf(workoutExercise.getId()))
                .removeValue()
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void getWorkoutExercises(long scheduleId, IWorkoutExercisesRepository.GetWorkoutExerciseCallback callback) {

        ValueEventListener workoutExerciseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DatabaseReference onlineReference = FirebaseDatabase.getInstance()
                        .getReference(".info/connected");
                onlineReference.get().addOnSuccessListener(status -> {
                    boolean connected = status.getValue(Boolean.class);
                    if (!connected) {
                        callback.onDataNotAvailable();
                    }
                });


                if (dataSnapshot.exists()) {
                    List<WorkoutExercise> workoutExercises = new ArrayList<>();
                    for (DataSnapshot scheduleSnapshot : dataSnapshot.getChildren()) {
                        WorkoutExercise exercise = scheduleSnapshot.getValue(WorkoutExercise.class);
                        workoutExercises.add(exercise);
                    }
                    callback.onWorkoutExercisesLoaded(workoutExercises);

                } else {
                    callback.onWorkoutExercisesLoaded(new ArrayList<>());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        };

        databaseReference.child("schedules")
                .child(String.valueOf(scheduleId))
                .child("workoutExercises")
                .addValueEventListener(workoutExerciseListener);

    }

}
