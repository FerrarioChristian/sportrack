package it.unimib.icasiduso.sportrack.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

@Dao
public interface ScheduleDao {
    @Query("SELECT * FROM schedule")
    List<Schedule> getAll();

    @Query("SELECT * FROM schedule WHERE userId IN (:userId)")
    List<Schedule> getSchedulesByUserId(String userId);

    @Upsert
    List<Long> insertScheduleList(List<Schedule> scheduleList);

    @Insert
    void insertAll(Schedule... schedules);

    @Delete
    void deleteSchedule(Schedule schedule);
    //eliminare anche tutti i workoutexercises con lo stesso scheduleId
}
