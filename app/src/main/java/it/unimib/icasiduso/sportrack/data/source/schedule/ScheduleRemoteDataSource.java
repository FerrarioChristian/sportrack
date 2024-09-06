package it.unimib.icasiduso.sportrack.data.source.schedule;


import static it.unimib.icasiduso.sportrack.utils.Constants.FIREBASE_DATABASE;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.schedule.IScheduleRepository;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public class ScheduleRemoteDataSource implements IScheduleDataSource.Remote {

    private static final String TAG = ScheduleRemoteDataSource.class.getSimpleName();

    private final DatabaseReference databaseReference;
    ;

    public ScheduleRemoteDataSource() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference(FIREBASE_DATABASE);
    }

    @Override
    public void newSchedule(Schedule schedule, IScheduleRepository.SaveScheduleCallback callback) {
        databaseReference.child(schedule.getUserId()).child("schedules").child(String.valueOf(schedule.getScheduleId())).setValue(schedule)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void deleteSchedule(Schedule schedule, IScheduleRepository.SaveScheduleCallback callback) {
        databaseReference.child(schedule.getUserId()).child("schedules").child(String.valueOf(schedule.getScheduleId())).removeValue()
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void getSchedules(String userId, IScheduleRepository.GetScheduleCallback callback) {
        ValueEventListener scheduleListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Schedule> schedules = new ArrayList<>();
                    for (DataSnapshot scheduleSnapshot : dataSnapshot.getChildren()) {
                        Schedule schedule = scheduleSnapshot.getValue(Schedule.class);
                        schedules.add(schedule);
                    }
                    callback.onSchedulesLoaded(schedules);

                    // Remove listener if you only need the initial data
                    databaseReference.child(userId).child("schedules").removeEventListener(this);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        };

        databaseReference.child(userId).child("schedules").addValueEventListener(scheduleListener);
    }

    @Override
    public void deleteUserSchedules(String userId, IScheduleRepository.SaveScheduleCallback callback) {
        databaseReference.child(userId).removeValue();
        callback.onSuccess();
    }
}
