package it.unimib.icasiduso.sportrack.ui.schedule;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapters.ScheduleRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.data.repository.schedule.IScheduleRepository;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.schedule.ScheduleViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.schedule.ScheduleViewModelFactory;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ScheduleFragment extends Fragment implements ScheduleRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = ScheduleFragment.class.getSimpleName();

    private ScheduleViewModel scheduleViewModel;
    private ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            /*
            workoutExerciseRepository.deleteWorkoutExercisesByScheduleId(scheduleList.get(position).getScheduleId());
            scheduleRepository.deleteSchedule(scheduleList.get(position));
            scheduleViewModel.deleteSchedule(position);
            scheduleRecyclerViewAdapter.notifyItemRemoved(position);
            */

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.md_theme_errorContainer_mediumContrast))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .setActionIconTint(ContextCompat.getColor(requireActivity(), R.color.md_theme_onErrorContainer))
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IScheduleRepository scheduleRepository = ServiceLocator.getInstance().getScheduleRepository();
        ScheduleViewModelFactory factory = new ScheduleViewModelFactory(scheduleRepository);
        scheduleViewModel = new ViewModelProvider(requireActivity(), factory).get(ScheduleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewScheduleList = view.findViewById(R.id.recyclerview_schedule_list);
        scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(this);
        recyclerViewScheduleList.setAdapter(scheduleRecyclerViewAdapter);

        observeViewModel();

        FloatingActionButton newScheduleButton = view.findViewById(R.id.newScheduleFAB);
        newScheduleButton.setOnClickListener(v -> {
            View customView = LayoutInflater.from(getContext()).inflate(R.layout.new_schedule_dialog, null);

            new MaterialAlertDialogBuilder(requireContext()).setTitle(getString(R.string.new_schedule)).setView(customView).setNegativeButton("Close", (dialog, which) -> {
                    })
                    .setPositiveButton(R.string.add, (dialog, which) -> {
                        TextInputLayout scheduleName = customView.findViewById(R.id.scheduleName);
                        MaterialButtonToggleGroup scheduleDifficulty = customView.findViewById(R.id.difficultyButton);

                        MaterialButton selectedButton = customView.findViewById(scheduleDifficulty.getCheckedButtonId());
                        String difficulty = selectedButton.getText().toString();
                        //TODO verificare input
                        scheduleViewModel.newSchedule(new Schedule(scheduleName.getEditText().getText().toString(), difficulty));
                    })
                    .show();

        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewScheduleList);
    }

    public void observeViewModel() {
        scheduleViewModel.getSchedules("").observe(getViewLifecycleOwner(), result -> {
            scheduleRecyclerViewAdapter.setSchedules(result);
        });


        ProgressBar progressBar = requireView().findViewById(R.id.schedule_progress_bar);
        scheduleViewModel.getIsLoadingLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onScheduleClick(Schedule schedule) {
        ScheduleFragmentDirections.ActionScheduleFragmentToListWorkoutExercisesFragment action = ScheduleFragmentDirections.actionScheduleFragmentToListWorkoutExercisesFragment(schedule.getScheduleId());
        Navigation.findNavController(requireView()).navigate(action);
    }
}