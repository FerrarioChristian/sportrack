package it.unimib.icasiduso.sportrack.model.schedule;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Schedule{
    @PrimaryKey (autoGenerate = true)
    private long scheduleId;
    private String name;
    private String difficulty;
  
    public Schedule(String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}