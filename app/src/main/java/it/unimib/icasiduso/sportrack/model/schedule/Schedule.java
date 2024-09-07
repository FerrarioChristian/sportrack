package it.unimib.icasiduso.sportrack.model.schedule;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(indices = {@Index(value = {"userId", "scheduleId"}, unique = true)})
public class Schedule {
    @PrimaryKey(autoGenerate = true)
    private long scheduleId;
    private String userId;
    private String name;
    private String difficulty;

    public Schedule() {
    }

    @Ignore
    public Schedule(String userId, String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}