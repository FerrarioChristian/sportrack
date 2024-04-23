package it.unimib.icasiduso.sportrack.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

@Dao
public interface ScheduleDao {
    @Query("SELECT * FROM schedule")
    List<Schedule> getAll();

    @Upsert
    List<Long> insertScheduleList(List<Schedule> scheduleList);

    @Delete
    void delete(Schedule schedule);
}
