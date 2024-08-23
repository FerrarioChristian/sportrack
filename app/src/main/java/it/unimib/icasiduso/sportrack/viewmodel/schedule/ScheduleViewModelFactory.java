package it.unimib.icasiduso.sportrack.viewmodel.schedule;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.icasiduso.sportrack.data.repository.schedule.IScheduleRepository;

public class ScheduleViewModelFactory implements ViewModelProvider.Factory{
    private final IScheduleRepository iScheduleRepository;

    public ScheduleViewModelFactory(IScheduleRepository iScheduleRepository){
        this.iScheduleRepository = iScheduleRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ScheduleViewModel(iScheduleRepository);
    }
}
