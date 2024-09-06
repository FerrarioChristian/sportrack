package it.unimib.icasiduso.sportrack.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertScheduleList(List<Schedule> scheduleList);

    @Query("DELETE FROM schedule WHERE userId = :userId")
    void deleteUserSchedules(String userId);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Schedule... schedules);

    @Delete
    void deleteSchedule(Schedule schedule);
}
