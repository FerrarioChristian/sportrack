package it.unimib.icasiduso.sportrack.viewmodel.schedule;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.icasiduso.sportrack.data.repository.schedule.IScheduleRepository;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public class ScheduleViewModel extends ViewModel implements IScheduleRepository.ScheduleCallback {
    private static final String TAG = ScheduleViewModel.class.getSimpleName();

    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    //TODO Cambiare List<Schedule> con <Result> e gestire le eccezioni
    private final MutableLiveData<List<Schedule>> schedulesLiveData = new MutableLiveData<>();

    private final IScheduleRepository scheduleRepository;

    public ScheduleViewModel(IScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void setIsLoading(boolean isLoading) {
        if (isLoading) {
            isLoadingLiveData.postValue(true);
        } else {
            isLoadingLiveData.postValue(false);
        }
    }

    public MutableLiveData<List<Schedule>> getSchedules(String userId) {
        setIsLoading(true);
        scheduleRepository.getSchedules(userId, this);
        return schedulesLiveData;
    }

    public void newSchedule(Schedule schedule) {
        scheduleRepository.newSchedule(schedule, this);
    }

    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.deleteSchedule(schedule, this);
    }

    public MutableLiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public MutableLiveData<List<Schedule>> getSchedulesLiveData() {
        return schedulesLiveData;
    }

    public IScheduleRepository getScheduleRepository() {
        return scheduleRepository;
    }

    @Override
    public void onSuccess() {
        //TODO: Implement
    }

    @Override
    public void onSuccess(List<Schedule> scheduleList) {
        setIsLoading(false);
        schedulesLiveData.postValue(scheduleList);
    }

    @Override
    public void onFailure(String errorMessage) {
        //TODO: Implement
    }
}
